package com.zking.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zking.dto.Result;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

//异常拦截
public class MyExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(@NotNull HttpServletRequest request,
                                         @NotNull HttpServletResponse response, Object handler,
                                         @NotNull Exception ex) {
        if (ex instanceof ConstraintViolationException){
            response.setContentType("application/json;charset=UTF-8");
            ConstraintViolationException exception = (ConstraintViolationException) ex;
            try {
                Result<Void> result = new Result<>(true, ex.getMessage(), null);
                response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                return new ModelAndView();
            } catch (IOException e) {
                return null;
            }
        }else if (ex instanceof MethodArgumentNotValidException){
            response.setContentType("application/json;charset=UTF-8");
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            try {
                StringBuilder message = new StringBuilder();
                Result<Void> result = new Result<>(true, ex.getMessage(), null);
                response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                return new ModelAndView();
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
}
