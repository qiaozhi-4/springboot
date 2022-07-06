package com.zking.controller;

import com.zking.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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


//    @DenyAll 拒绝访问
//    @PermitAll 允许访问
//    @RolesAllowed == @Secured 安全认证，设置角色
//    @PreAuthorize 支持spEL表达式，类似@Secured注解，进入方法之前验证授权
//    @PostAuthorize 支持spEL表达式，检查授权方法之后才被执行
//    @PreFilter 在方法执行之前执行，可调用使用方法的参数
//    @PostFilter 在方法执行之后执行，可调用使用方法的返回值

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
