package com.zking.controller;

import com.zking.entity.User;
import com.zking.exception.MyException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.management.RuntimeMBeanException;

@RestController
//自动生成无参带参构造
@RequiredArgsConstructor
//一级路径
@RequestMapping()
public class Controller1 {

    @GetMapping("t1")
    public User t1() {
       if (Math.random() > 0.5){
           throw new RuntimeException("gg");
       }
       return new User();
    }

    @GetMapping("t2")
    public User t2() {
       if (Math.random() > 0.5){
           throw new MyException("我的异常");
       }
       return new User();
    }


}
