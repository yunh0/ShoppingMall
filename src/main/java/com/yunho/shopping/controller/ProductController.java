package com.yunho.shopping.controller;

import com.yunho.shopping.domain.Category;
import com.yunho.shopping.domain.ProductImg;
import com.yunho.shopping.dto.CustomPrincipal;
import com.yunho.shopping.dto.MemberDto;
import com.yunho.shopping.dto.ProductDto;
import com.yunho.shopping.dto.request.ProductRequest;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/product/{productId}")
    public String showProductDetail(@PathVariable Long productId, Model model){
        List<ProductImg> images = productImgService.getProductImages(productId);
        ProductDto productDto = productService.findByProductId(productId);
        ProductResponse productResponse = ProductResponse.from(productDto, images);

        model.addAttribute("productResponse", productResponse);

        return "/productDetail";
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

    @GetMapping("/seller/sellerPage/product/categories/{parentId}")
    @ResponseBody
    public ResponseEntity<List<Category>> getSubCategories(@PathVariable Long parentId){
        List<Category> subCategories = categoryService.getSubCategories(parentId);

        return ResponseEntity.ok(subCategories);
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
}
