package com.zking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
//自动生成无参带参构造
@RequiredArgsConstructor
//一级路径
@RequestMapping("/job05")
public class Controller3 {
    @Value("${upload.location}")
    private String location;

    //跳转主页
    @GetMapping("index")
    public String index() {
        return "index";
    }

    //跳转主页
    @GetMapping("index2")
    public String index2() {
        return "index2";
    }

    //跳转主页
    @GetMapping("index3")
    public String index3() {
        return "index3";
    }

    //上传图片
    @PostMapping("updateFile")
    public String updateFile(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        //随机文件名
        String path = "/" + UUID.randomUUID() + file.getOriginalFilename();
        //location为配置文件配置的路径
        File dest = new File(location, path);
        //文件上传到准备好的文件
        file.transferTo(dest);
        redirectAttributes.addFlashAttribute("path", path);
        return "redirect:index";
    }

}
