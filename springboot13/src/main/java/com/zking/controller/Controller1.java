package com.zking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;

@PermitAll
@Controller
public class Controller1 {


    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("login.html")
    public String login() {
        return "login";
    }

    @GetMapping("success.html")
    public String success() {
        return "success";
    }

    @GetMapping("error.html")
    public String error() {
        return "error";
    }

    @GetMapping("403.html")
    public String error403() {
        return "403";
    }

    @GetMapping("/t1")
    public String test1(HttpSession session,Model model) {
        model.addAttribute("path","test1");
        return "test";
    }

    @GetMapping("/t2")
    public String test2(HttpSession session,Model model) {
        model.addAttribute("path","test2");
        return "test";
    }

    @GetMapping("/t3")
    public String test3(HttpSession session,Model model) {
        model.addAttribute("path","test3");
        return "test";
    }

    @GetMapping("/t4")
    public String test4(HttpSession session,Model model) {
        model.addAttribute("path","test4");
        return "test";
    }

    @RolesAllowed("user")//指定要这个角色
    @GetMapping("/t11")
    public String test11(HttpSession session,Model model) {
        model.addAttribute("path","test11");
        return "test";
    }

    @GetMapping("/t22")
    public String test22(HttpSession session,Model model) {
        model.addAttribute("path","test22");
        return "test";
    }

    @GetMapping("/t33")
    public String test33(HttpSession session,Model model) {
        model.addAttribute("path","test33");
        return "test";
    }

    @GetMapping("/t44")
    public String test44(HttpSession session,Model model) {
        model.addAttribute("path","test44");
        return "test";
    }


}
