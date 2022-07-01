package com.zking.advice;

import com.mysql.cj.log.Log;
import com.mysql.cj.log.LogFactory;
import javafx.util.converter.FormatStringConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

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
@Slf4j//相当于直接生成一个Log对象
public class MyControllerAdvice {

    @ModelAttribute
    public void global(Model model, HttpServletRequest request){
        model.addAttribute("c0","0000");
        setLog(request);
    }

    @InitBinder
    public void binder(WebDataBinder binder){
        //Date日期转换规则
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),false));
        //list集合转换规则
        binder.registerCustomEditor(List.class,new MyPropertyEditorSupport());
    }

    //注解这些异常来这里处理
    @ExceptionHandler({Exception.class})
    //捕获异常之后状态码设置为400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleExl(HttpServletResponse response,Exception e) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("你爆了");
        response.getWriter().write(e.getMessage());
        return new ModelAndView();
    }


    public void setLog(HttpServletRequest request){
        StringBuilder stringBuilder = new StringBuilder();
        String date = new SimpleDateFormat("yyyy-MM-dd E HH:mm:ss ").format(new Date());
        String path = request.getRequestURL().toString();
        stringBuilder.append("{");
        //1.获取请求正文名称的枚举
        Enumeration<String> names = request.getParameterNames();
        //2.遍历正文名称的枚举
        while(names.hasMoreElements()){
            String name = names.nextElement();
            String value = request.getParameter(name);
            stringBuilder.append(name).append(":").append(value);
        }
        stringBuilder.append("}");
        log.info(date + path + " " + stringBuilder);
    }

}
