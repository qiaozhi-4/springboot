package com.zking.controller;

import com.zking.entity.User;
import com.zking.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
//自动生成无参带参构造
@RequiredArgsConstructor
//一级路径
@RequestMapping
public class Controller2 {

    private final IUserService userService;

    //跳转注册页
    @GetMapping("register")
    public String register() {
        return "register";
    }

    //注册提交验证
    @PostMapping("registerVerify")
    public String registerVerify(String username, String password, Model model) {
        User user = userService.register(username, password);
        if (user == null){
            model.addAttribute("info","注册失败");
            return "register";
        }
        model.addAttribute("info","注册成功");
        return "login";
    }

    //跳转登录页
    @GetMapping({"login", ""})
    public String login() {
        return "login";
    }

    //登录提交验证
    @PostMapping("loginVerify")
    public String loginVerify(String username, String password, HttpSession session, Model model) {
        User user = userService.login(username,password);
        if (user==null){
            model.addAttribute("info","登录失败");
            return "login";
        }
        session.setAttribute("user",user);
        model.addAttribute("info","登录成功");
        return "home";
    }
}
