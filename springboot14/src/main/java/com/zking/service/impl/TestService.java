package com.zking.service.impl;

import com.zking.entity.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestService {

    @Async
    public void testAsync(){
        System.out.println("异步当前线程" + Thread.currentThread().getName());
    }

    public void testAsync2(){
        System.out.println("当前线程" + Thread.currentThread().getName());
    }

//    @Scheduled(fixedDelay = 5000)//每5秒运行一次
//    public void test3(){
//        System.out.println("每5秒运行一次");
//    }
//
//    private int count;
//    @Scheduled(fixedRate = 3000, initialDelay = 5000)//5秒后每3秒运行异常
//    public void test4(){
//        System.out.println("5秒后每3秒运行异常" + count++);
//    }


    //自定义条件，实现Condition
    private static class MyCondition implements Condition{

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment environment = context.getEnvironment();
            String platform = environment.getProperty("os");
            System.out.println("你的平台是：" + platform);
            return Math.random() > 0.5;
        }
    }

    //按条件注入bean，自定义条件
    @Conditional(MyCondition.class)
    @Bean
    public User User1(){
        User user = new User();
        user.setUsername("狗管理");
        return user;
    }

    //按条件注入：如果user类型的bean不存在就注入
    @ConditionalOnMissingBean(User.class)
    @Bean
    public User User2(){
        User user = new User();
        user.setUsername("狗群员");
        return user;
    }


}
