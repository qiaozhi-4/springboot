package com.zking.advice;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice(annotations = {Controller.class})  // 指定被增强注解类型
public class MyControllerAdvice {

    //注解这些异常来这里处理
    @ExceptionHandler({UnauthorizedException.class})
    public String handleExl(UnauthorizedException e, Model model) throws IOException {
        model.addAttribute("error","没有权限");
        return "/error1";
    }

    //注解这些异常来这里处理
    @ExceptionHandler({UnauthenticatedException.class})
    public String handleExl(UnauthenticatedException e, Model model) throws IOException {
        model.addAttribute("error","没有认证");
        return "/error1";
    }



    // 没有通过认证爆发的异常
    @ExceptionHandler({AuthenticationException.class})
    public String exception(Exception e, Model model) {
        model.addAttribute("error", "必须通过验证才能访问该页面！");
        return "/403";
    }


}
