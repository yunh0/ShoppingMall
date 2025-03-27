package com.yunho.shopping.service;

import com.yunho.shopping.domain.Category;
import com.yunho.shopping.domain.Member;
import com.yunho.shopping.domain.Product;
import com.yunho.shopping.dto.MemberDto;
import com.yunho.shopping.dto.ProductDto;
import com.yunho.shopping.dto.request.ProductRequest;
import com.yunho.shopping.dto.request.UpdateProductRequest;
import com.yunho.shopping.dto.response.ProductResponse;
import com.yunho.shopping.repository.CategoryRepository;
import com.yunho.shopping.repository.MemberRepository;
import com.yunho.shopping.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImgService productImgService;
    private final PurchaseHistoryService purchaseHistoryService;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public ProductDto findByProductId(Long productId){
        return ProductDto.from(
                productRepository.getReferenceById(productId)
        );
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> searchProducts(String userId, Pageable pageable){
        return productRepository.findByMember_UserIdContaining(userId, pageable)
                .map(ProductDto::from);
    }

    public void saveProduct(ProductDto productDto, List<MultipartFile> images){
        Member member = memberRepository.getReferenceById(productDto.memberDto().userId());

        Product product = productDto.toEntity(member);
        productRepository.save(product);

        productImgService.uploadImg(product, images);
    }

    public Map<Category, List<ProductResponse>> getProductsByTopCategory(){
        return categoryRepository.findByDepthIsOne().stream()
                .collect(Collectors.toMap(
                        category -> category,
                        category -> productRepository.findTop6ByTopCategory(category).stream()
                                .map(product -> ProductResponse.from(ProductDto.from(product), productImgService.getProductImagesPath(
                                        productImgService.getProductImages(product.getProductId())
                                )))
                                .collect(Collectors.toList())
                ));
    }

    public Page<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable){
        return productRepository.findByCategory_CategoryId(categoryId, pageable)
                .map(product -> ProductResponse.from(ProductDto.from(product), productImgService.getProductImagesPath(
                        productImgService.getProductImages(product.getProductId())
                )));
    }

    public void buyProduct(Long productId, String userId, int quantity){
        Product product = productRepository.findWithPessimisticLockById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        if(product.getCount() < quantity){
            throw new IllegalStateException("재고가 부족합니다.");
        }
        product.setCount(product.getCount() - quantity);

        purchaseHistoryService.savePurchaseHistory(product, userId, quantity);
    }

    public void updateProduct(Long productId, UpdateProductRequest productRequest, List<MultipartFile> newImages) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다. ID: " + productId));

        product.setProductName(productRequest.getProductName());
        product.setPrice(productRequest.getPrice());
        product.setInfo(productRequest.getInfo());
        product.setCount(productRequest.getCount());

        if (newImages != null && !newImages.isEmpty() && newImages.stream().anyMatch(file -> !file.isEmpty())) {
            productImgService.deleteImagesByProduct(productId);
            productImgService.uploadImg(product, newImages);
        }
    }
}
