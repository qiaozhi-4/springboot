package com.zking.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

/*
自定义SpringSecurity相关配置：
    1. 必须有一个且只有一个UserDetailsService的Bean
    2. 配置核心安全拦截过滤器：SecurityFilterChain的Bean对象，自动注入一个默认的HttpSecurity
开启认证注解：
    @EnableWebSecurity 开启Web项目验证注解
    @EnableGlobalMethodSecurity 开启方法注解
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService, JdbcTokenRepositoryImpl repository) throws Exception {
        //1.必须有请求认证过滤配置
        http.authorizeRequests()
                        .anyRequest().permitAll();

        //2.自定义登录表单
        http.formLogin()
                .permitAll()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .failureUrl("/login.html?error")
                .defaultSuccessUrl("/success.html");

        //3.自定义登出页
        http.logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html?logout")
//                .invalidateHttpSession(true)
//                .deleteCookies("remember-me")
//                .logoutSuccessHandler((request, response, authentication) -> {})
                .addLogoutHandler((request, response, authentication) -> System.out.println("【日志】登出了"));

        //4.CSRF自动开启
//        http.csrf().disable();

        //5.记住我的功能
        http.rememberMe()//开启记住我的功能
                .tokenValiditySeconds(600)//有效时间【600秒】
                .userDetailsService(userDetailsService)
                .rememberMeParameter("remember-me")
                .tokenRepository(repository)
                .key("123");//唯一的一个key，自定义

        //6.登录权限异常处理
        http.exceptionHandling()
                .accessDeniedPage("/403.html");//自定义页面；无访问权限

        //7.其他配置
//        http.httpBasic()//默认开启

        //8.返回
        return http.build();
    }

    @Bean
    public JdbcTokenRepositoryImpl repository(DataSource dataSource){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        repository.setCreateTableOnStartup(false);
        return repository;
    }
}
