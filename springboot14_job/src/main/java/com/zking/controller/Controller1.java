package com.zking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;

//注释这个类下所有映射路径都能直接访问
@PermitAll
@Controller
@RequiredArgsConstructor
public class Controller1 {

    //主页
    @GetMapping
    public String index() {
        return "index";
    }

    //登录页
    @GetMapping("login.html")
    public String login(HttpSession session) {
        return "login";
    }

    //登录成功页
    @GetMapping("success.html")
    public String success() {
        return "success";
    }

    //抛出异常页
    @GetMapping("error.html")
    public String error() {
        return "error";
    }

    //权限不足页
    @GetMapping("403.html")
    public String error403() {
        return "403";
    }

    //不需要任何权限
    @GetMapping("/t1")
    public String test1(HttpSession session, Model model) {
        model.addAttribute("path", "test1");
        model.addAttribute("info", "不需要要任何权限");
        return "test";
    }

    //拒绝任何访问
    @DenyAll
    @GetMapping("/t2")
    public String test2(HttpSession session, Model model) {
        model.addAttribute("path", "test2");
        return "test";
    }

    //需要指定角色
    @RolesAllowed({"admin"})
    @GetMapping("/t3")
    public String test3(HttpSession session, Model model) {
        model.addAttribute("path", "test3");
        model.addAttribute("info", "需要admin角色");
        return "test";
    }

    //需要指定角色hasRole
    @RolesAllowed({"user"})
    @GetMapping("/t33")
    public String test33(HttpSession session, Model model) {
        model.addAttribute("path", "test33");
        model.addAttribute("info", "需要user角色");
        return "test";
    }

    //需要指定权限
    @PreAuthorize("hasAuthority('delete')")
    @GetMapping("/t4")
    public String test4(HttpSession session, Model model) {
        model.addAttribute("path", "test4");
        model.addAttribute("info", "需要delete权限");
        return "test";
    }

    //需要指定权限
    @PreAuthorize("hasAuthority('delete') or hasAuthority('read')")
    @GetMapping("/t44")
    public String test44(HttpSession session, Model model) {
        model.addAttribute("path", "test44");
        model.addAttribute("info", "需要delete权限或者read权限");
        return "test";
    }

    //需要指定权限
    @PreAuthorize("hasAuthority('delete') and hasAuthority('read')")
    @GetMapping("/t444")
    public String test444(HttpSession session, Model model) {
        model.addAttribute("path", "test44");
        model.addAttribute("info", "需要delete权限和read权限");
        return "test";
    }

}
