package com.zking.controller;

import com.zking.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class Controller1 {


    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/t1")
    public String test1(HttpSession session,Model model) {
        model.addAttribute("path","test1");
        return "test";
    }

    @GetMapping("/t2")
    public String test2(HttpSession session,Model model) {
        model.addAttribute("path","test2");
        return "test";
    }

    @GetMapping("/t3")
    public String test3(HttpSession session,Model model) {
        model.addAttribute("path","test3");
        return "test";
    }

    @GetMapping("/t4")
    public String test4(HttpSession session,Model model) {
        model.addAttribute("path","test4");
        return "test";
    }


}
