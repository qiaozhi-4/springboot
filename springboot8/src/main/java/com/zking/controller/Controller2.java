package com.zking.controller;

import com.zking.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//自动生成无参带参构造
@RequiredArgsConstructor
//一级路径
@RequestMapping("/t2")
@ResponseBody
public class Controller2 {

    @GetMapping({"a1"})
    public User test1(@ModelAttribute("c1") String c1,@ModelAttribute("c0") String c0) {
        return new User(1,c1,c0,10.0);
    }

}
