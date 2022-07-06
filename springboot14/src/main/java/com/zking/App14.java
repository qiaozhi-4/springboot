package com.zking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync//开启允许异步方法运行
@EnableScheduling//开启允许定时任务
public class App14 {
    public static void main(String[] args) {
        SpringApplication.run(App14.class, args);
    }
}