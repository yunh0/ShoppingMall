package com.yunho.shopping.controller;

import com.yunho.shopping.domain.Category;
import com.yunho.shopping.dto.CustomPrincipal;
import com.yunho.shopping.dto.MemberDto;
import com.yunho.shopping.dto.ProductDto;
import com.yunho.shopping.dto.request.ProductRequest;
import com.yunho.shopping.dto.response.ProductWithSellerResponse;
import com.yunho.shopping.service.CategoryService;
import com.yunho.shopping.service.MemberService;
import com.yunho.shopping.service.PaginationService;
import com.yunho.shopping.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller/sellerPage/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final MemberService memberService;
    private final PaginationService paginationService;

    @GetMapping
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

    @GetMapping("/save")
    public String saveProductView(Model model){
        List<Category> categoryList = categoryService.getCategoriesByDepth(1);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("productRequest", new ProductRequest());

        return "/saveProduct";
    }

    @GetMapping("/categories/{parentId}")
    @ResponseBody
    public ResponseEntity<List<Category>> getSubCategories(@PathVariable Long parentId){
        List<Category> subCategories = categoryService.getSubCategories(parentId);

        return ResponseEntity.ok(subCategories);
    }

    @PostMapping("/save")
    public String saveProduct(
            @AuthenticationPrincipal CustomPrincipal principal,
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

        productService.saveProduct(productDto);

        return "redirect:/seller/sellerPage/product";
    }
}
