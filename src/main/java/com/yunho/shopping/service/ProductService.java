package com.yunho.shopping.service;

import com.yunho.shopping.domain.Member;
import com.yunho.shopping.domain.Product;
import com.yunho.shopping.dto.ProductDto;
import com.yunho.shopping.repository.MemberRepository;
import com.yunho.shopping.repository.ProductImgRepository;
import com.yunho.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ProductImgService productImgService;

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
}
