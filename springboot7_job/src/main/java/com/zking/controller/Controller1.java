package com.zking.controller;

import com.zking.entity.User;
import com.zking.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
//自动生成无参带参构造
@RequiredArgsConstructor
//一级路径
@RequestMapping()
public class Controller1 {

    @GetMapping({"test1"})
    @ResponseBody
    public String test1() {
        if (Math.random() < 0.7){
            throw new CustomException();
        }
        return "test1";
    }

}
