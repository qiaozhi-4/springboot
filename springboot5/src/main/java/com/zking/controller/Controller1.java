package com.zking.controller;

import com.zking.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
//自动生成无参带参构造
@RequiredArgsConstructor
//一级路径
@RequestMapping("/t")
public class Controller1 {

    @GetMapping({"test1", ""})
    public String test1() {

        return "test1";
    }

    @GetMapping("test2")
    public String test2(Model model) {
        User user = new User();
        user.setId(1);
        user.setUsername("qz");
        user.setPassword("123");
        user.setMoney(100.0);
        model.addAttribute("user", user);
        model.addAttribute("html", "<b>111</b>");
        model.addAttribute("list", Arrays.asList("乔治", "汤姆", "杰瑞"));
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "卡莎");
        map.put(2, "金克斯");
        map.put(3, "崔丝塔娜");
        model.addAttribute("map", map);
        return "test1";
    }

    @GetMapping("users")
    public String test3(/*Integer id, String name, */Model model) {
        model.addAttribute("id", Math.random());
        model.addAttribute("name",  Math.random());
        return "test1";
    }

}
