package com.yunho.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController{

    @GetMapping("/")
    public String root(){
        return "forward:/index";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
