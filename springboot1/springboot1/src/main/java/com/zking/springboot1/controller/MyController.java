package com.zking.springboot1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class MyController {

    @ResponseBody
    @RequestMapping
    public String index(){
        return "hhhhhhh";
    }
}
