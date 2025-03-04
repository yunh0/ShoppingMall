package com.yunho.shopping.controller;

import com.yunho.shopping.domain.Category;
import com.yunho.shopping.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/api/category/top")
    public ResponseEntity<List<Category>> getTopCategories(){
        return ResponseEntity.ok(categoryService.getCategoriesByDepth(1));
    }

    @GetMapping("/api/category/{parentId}/sub")
    @ResponseBody
    public ResponseEntity<List<Category>> getSubCategories(@PathVariable Long parentId){
        return ResponseEntity.ok(categoryService.getSubCategories(parentId));
    }

    @GetMapping("/seller/sellerPage/product/categories/{parentId}")
    @ResponseBody
    public ResponseEntity<List<Category>> getSubCategoriesWithSeller(@PathVariable Long parentId){
        return ResponseEntity.ok(categoryService.getSubCategories(parentId));
    }


}
