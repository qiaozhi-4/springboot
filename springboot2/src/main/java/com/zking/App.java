package com.zking;

import com.zking.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Controller
@RequestMapping
@ConfigurationProperties(prefix = "test.jdbc")
@Getter @Setter
public class App {
    private List<User> users;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @ResponseBody
    @RequestMapping("/getUsers")
    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        users.add(new User(1,"乔治",18));
        users.add(new User(2,"佩奇",28));
        users.add(new User(3,"汤姆",8));
        users.add(new User(4,"杰瑞",38));
        return users;
    }

    @ResponseBody
    @RequestMapping("/ymlUsers")
    public List<User> ymlUsers(){
        return users;
    }
}