package com.zking.controller;

import com.zking.dto.Result;
import com.zking.entity.User;
import com.zking.validate.MyValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotBlank;

@RestController
//自动生成无参带参构造
@RequiredArgsConstructor
//一级路径
@RequestMapping()
@Validated//开启验证
public class Controller1 {
    @Value("${upload.location}")
    private String location;

    @PostMapping("login")
    public Result<User> login(String username, String password) {
       return null;
    }

    @GetMapping("test1")
    public Result<User> test1(@NotBlank(message = "不能为空") String username) {
       return new Result<>(true,username,null);
    }

    @PostMapping("test2")
    public Result<User> test2(@RequestBody @Validated User user) {
       return new Result<>(true,"",user);
    }

    @PostMapping("job1")
    public Result<User> job1(@RequestBody @MyValidate User user) {
       return new Result<>(true,"",user);
    }

}
