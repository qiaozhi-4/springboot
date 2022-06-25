package com.zking.controller;

import com.zking.entity.User;
import com.zking.mapper.IUserMapper;
import com.zking.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @RestController = @ResponseBody + @Controller
@RestController
@RequiredArgsConstructor
public class UserController {
    private final IUserMapper mapper;
    private final IUserService userService;

    @PostMapping("register")
    public User register(String username,String password) {
        return userService.register(username,password);
    }

    @PutMapping("update")
    public boolean update(User user) {
        return userService.updateById(user);
    }

    @DeleteMapping("delete")
    public boolean delete(Integer id) {
        return userService.removeById(id);
    }

    @RequestMapping("users")
    public List<User> users() {
        return mapper.findAll();
    }

    @GetMapping("user/{id}")
    public User user(@PathVariable("id") int id) {
        return mapper.findById(id);
    }

    /*
    SpringBoot文件上传：
    1. 配置：spring.servlet.multipart.xxx=yyy
    2. Servlet中Part类型可以在SpringBoot中使用MultipartFile
    3. @PostMapping = @RequestMapping(path = "xxx", method = RequestMethod.POST)
        @GetMapping = @RequestMapping(path = "xxx", method = RequestMethod.GET)
     */
    // @RequestMapping(value = "upload", method = RequestMethod.POST)
    @PostMapping("upload")
    public Map<String, Object> upload(String username,
                                      @RequestParam MultipartFile file) throws IOException {
        // 可以直接获取文件名，并且一行代码将文件保存到指定位置
        File image = new File("D:\\user\\train\\java\\springboot\\springboot3\\src\\main\\resources\\static",
                file.getOriginalFilename());
        file.transferTo(image);
        // 返回
        Map<String, Object> map = new HashMap<>();
        map.put("name", username);
        map.put("file", file.getOriginalFilename());
        return map;
    }
}
