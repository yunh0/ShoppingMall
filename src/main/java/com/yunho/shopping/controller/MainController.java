package com.yunho.shopping.controller;

import com.yunho.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController{

    private final ProductService productService;

    @GetMapping("/")
    public String root(){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("categoryProducts", productService.getProductsByTopCategory());
        return "index";
    }
}
