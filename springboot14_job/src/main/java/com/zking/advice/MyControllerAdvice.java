package com.zking.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice  // 指定被增强注解类型
public class MyControllerAdvice {

    //注解这些异常来这里处理
    @ExceptionHandler({RuntimeException.class,Exception.class})
    public String handleExl(Exception e, Model model) throws IOException {
        model.addAttribute("error","爆掉了");
        model.addAttribute("info",e.getMessage());
        return "error";
    }

}
