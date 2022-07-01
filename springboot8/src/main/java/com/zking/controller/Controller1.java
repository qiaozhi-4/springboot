package com.zking.controller;

import com.zking.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@RestController
//自动生成无参带参构造
@RequiredArgsConstructor
//一级路径
@RequestMapping("/t1")
@ResponseBody
@Slf4j//相当于直接生成一个Log对象
public class Controller1 {
    //记录访问时间和路径、参数信息

    @ModelAttribute
    public void share(Model model){
        model.addAttribute("c1","11111");
    }

    @GetMapping({"a1"})
    public User test1(@ModelAttribute("c1") String c1,@ModelAttribute("c0") String c0, HttpServletRequest request) {
//       setLog(request);
        return new User(1,c1,c0,10.0);
    }

    @GetMapping({"a2"})
    public Date test2(Date date) {
        log.info("ggg");
        return date;
    }

    @GetMapping({"a3"})
    public List<String> test3(@RequestParam List<String > hobby) {
        return hobby;
    }


}
