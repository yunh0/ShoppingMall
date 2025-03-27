package com.yunho.shopping.controller;

import com.yunho.shopping.domain.Category;
import com.yunho.shopping.domain.Product;
import com.yunho.shopping.domain.ProductImg;
import com.yunho.shopping.dto.CustomPrincipal;
import com.yunho.shopping.dto.MemberDto;
import com.yunho.shopping.dto.ProductDto;
import com.yunho.shopping.dto.request.ProductRequest;
import com.yunho.shopping.dto.request.UpdateProductRequest;
import com.yunho.shopping.dto.response.ProductResponse;
import com.yunho.shopping.dto.response.ProductWithSellerResponse;
import com.yunho.shopping.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final MemberService memberService;
    private final PaginationService paginationService;
    private final ProductImgService productImgService;
    private final PurchaseHistoryService purchaseHistoryService;

    @GetMapping("/product/category/{categoryId}")
    public String showProductWithCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 12, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ){
        Page<ProductResponse> products = productService.getProductsByCategory(categoryId, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), products.getTotalPages());
        String categoryName = categoryService.getCategoryById(categoryId).getName();

        model.addAttribute("products", products);
        model.addAttribute("paginationBarNumbers", barNumbers);
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("categoryName", categoryName);


        return "/products";
    }

    @GetMapping("/product/{productId}")
    public String showProductDetail(
            @PathVariable Long productId,
            @AuthenticationPrincipal CustomPrincipal principal,
            Model model
    ){
        List<ProductImg> images = productImgService.getProductImages(productId);
        List<String> imagesPath = productImgService.getProductImagesPath(images);
        ProductDto productDto = productService.findByProductId(productId);
        ProductResponse productResponse = ProductResponse.from(productDto, imagesPath);

        if(principal == null){
            model.addAttribute("isOwner", false);
        }
        else{
            Boolean isOwner = productDto.memberDto().userId().equals(principal.getUsername());
            model.addAttribute("isOwner", isOwner);
        }

        model.addAttribute("productResponse", productResponse);

        return "/productDetail";
    }

    @PostMapping("/product/{productId}/buy")
    public String buyProduct(
            @PathVariable Long productId,
            @AuthenticationPrincipal CustomPrincipal principal,
            @RequestParam("quantity") int quantity
    ){
        productService.buyProduct(productId, principal.getUsername(), quantity);

        return "redirect:/product/{productId}";
    }

    @GetMapping("/seller/sellerPage/product")
    public String showSellerPage(
            @AuthenticationPrincipal CustomPrincipal principal,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ){
        Page<ProductWithSellerResponse> products = productService.searchProducts(principal.getUsername(), pageable).map(ProductWithSellerResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), products.getTotalPages());

        model.addAttribute("products", products);
        model.addAttribute("paginationBarNumbers", barNumbers);

        return "/sellerPage";
    }

    @GetMapping("/seller/sellerPage/product/save")
    public String saveProductView(Model model){
        List<Category> categoryList = categoryService.getCategoriesByDepth(1);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("productRequest", new ProductRequest());

        return "/saveProduct";
    }

    @PostMapping("/seller/sellerPage/product/save")
    public String saveProduct(
            @AuthenticationPrincipal CustomPrincipal principal,
            @RequestParam("images") List<MultipartFile> images,
            @ModelAttribute("productRequest") @Valid ProductRequest productRequest,
            BindingResult bindingResult,
            Model model
    ){
        if(bindingResult.hasErrors()){
            model.addAttribute("errors", bindingResult.getAllErrors());
            List<Category> categoryList = categoryService.getCategoriesByDepth(1);
            model.addAttribute("categoryList", categoryList);

            return "/saveProduct";
        }

        Category category = categoryService.getCategoryById(productRequest.getCategoryId());
        MemberDto memberDto = memberService.searchMember(principal.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. username: " + principal.getUsername()));

        ProductDto productDto = productRequest.toDto(memberDto, category);

        productService.saveProduct(productDto, images);

        return "redirect:/seller/sellerPage/product";
    }

    @GetMapping("/seller/sellerPage/product/{productId}/update")
    public String updateProductView(
            @PathVariable Long productId,
            Model model
    ){
        ProductDto productDto = productService.findByProductId(productId);
        List<String> images = productImgService.getProductImagesPath(
                productImgService.getProductImages(productId)
        );

        UpdateProductRequest updateProductRequest = UpdateProductRequest.from(productDto);
        ProductResponse productResponse = ProductResponse.from(productDto, images);

        model.addAttribute("updateProductRequest", updateProductRequest);
        model.addAttribute("productResponse", productResponse);

        return "/updateProduct";
    }

    @PostMapping("/seller/sellerPage/product/{productId}/update")
    public String updateProduct(
            @PathVariable Long productId,
            @RequestParam(value = "newImages", required = false) List<MultipartFile> newImages,
            @ModelAttribute("updateProductRequest") @Valid UpdateProductRequest updateProductRequest,
            BindingResult bindingResult,
            Model model
    ) {
        if(bindingResult.hasErrors()){
            ProductDto productDto = productService.findByProductId(productId);
            List<String> images = productImgService.getProductImagesPath(
                    productImgService.getProductImages(productId)
            );

            ProductResponse productResponse = ProductResponse.from(productDto, images);

            model.addAttribute("updateProductRequest", updateProductRequest);
            model.addAttribute("productResponse", productResponse);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "/updateProduct";
        }

        productService.updateProduct(productId, updateProductRequest, newImages);

        return "redirect:/product/" + productId;
    }
}
