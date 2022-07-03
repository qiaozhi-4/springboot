package com.zking.advice;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/*
使用增强注解：
    @ControllerAdvice 专门用于增强控制器的注解
    @ExceptionHandler 注解方法，表示该方法用于处理指定异常
    方法可以传递指定类型的异常作为参数，返回值也可以使用JSON或者View
使用优势：
    1. 不用配置WebMvcConfigurer接口，不用实现HandlerExceptionResolver接口
    2. 单独定义在任意@ControllerAdvice注解的类上
    3. 可以单独处理不同类型的异常，不同异常可以不同方法
    4. 方法签名任意：返回值任意、参数任意，可以使用response，也可以使用view视图，也可以使用json
    5. 可以指定增强的控制器
 */
// @Component // 这里不用@Component这些注解！
@ControllerAdvice(annotations = {Controller.class})  // 指定被增强注解类型
public class MyControllerAdvice {

    //没有授权、没有指定角色、权限
    @ExceptionHandler({UnauthorizedException.class})
    public String authorize(UnauthorizedException e, Model model) {
        model.addAttribute("error", "没有权限【角色】访问该页面！");
        return "error";
    }

    // 没有通过认证爆发的异常
    @ExceptionHandler({UnauthenticatedException.class})
    public String authenticate(UnauthenticatedException e, Model model) {
        model.addAttribute("error", "必须通过验证才能访问该页面！");
        return "error";
    }

    // 没有通过认证爆发的异常
    @ExceptionHandler({Exception.class})
    public String exception(UnauthenticatedException e, Model model) {
        model.addAttribute("error", "必须通过验证才能访问该页面！");
        return "error";
    }

}
