package com.zking.advice;

import com.zking.exception.CustomException;
import kotlin.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
// @ResponseBody
public class MyAdviceController {

    //注解这些异常来这里处理
    @ExceptionHandler({RuntimeException.class,Exception.class})
    public ModelAndView handleExl(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("你爆了");
        return new ModelAndView();
    }

    @ExceptionHandler({CustomException.class})
    public ModelAndView  handleEx2(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("异常了");
        return new ModelAndView();
    }
}
