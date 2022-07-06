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

    //加密需要的bean
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    //核心
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService, JdbcTokenRepositoryImpl repository) throws Exception {
        //1.必须有请求认证过滤配置
        http.authorizeRequests()
                        .anyRequest().permitAll();//允许任何请求访问

        //2.自定义登录表单
        http.formLogin()
                .permitAll()// 保证页面可以访问，因为默认情况下都必须登录
                .loginPage("/login.html")// 自定义的登录页：有表单，对应Controller的映射
                .loginProcessingUrl("/login") // 固定死：内置的请求处理路径，那么在表单上action必须用这个
                .failureUrl("/login.html?error")// 登录失败，任意
                .defaultSuccessUrl("/success.html");// 自定义成功后转发的页面，任意

        //3.自定义登出页
        http.logout()
                .logoutUrl("/logout")// 固定死：内置请求处理路径，用在登出表单action中，登出表单要加csrf
                .logoutSuccessUrl("/login.html?logout")// 自定义登录成功，和下面的二选一，随意
//                .logoutSuccessHandler((request, response, authentication) -> response.sendRedirect("/login.html?logout"))
//                .invalidateHttpSession(true)// 登出后默认就会清除会话
//                .deleteCookies("remember-me")// 登出后可以手动删除一些自定义的cookie
                .addLogoutHandler((request, response, authentication) -> System.out.println("【日志】登出了"));

        //4.CSRF自动开启
//        http.csrf().disable();

        //5.记住我的功能
        http.rememberMe()//开启记住我的功能：1.jdbc依赖；2.mysql；3.配置JdbcTokenRepositoryImpl
                .tokenValiditySeconds(600)//有效时间【600秒】
                .userDetailsService(userDetailsService)//Cookie关联用户服务
                .rememberMeParameter("remember-me")// 默认，可以自定义参数名
                .tokenRepository(repository)// 自动注入JdbcTokenRepositoryImpl的Bean
                .key("123");//唯一的一个key，自定义

        //6.登录权限异常处理
        http.exceptionHandling()
                .accessDeniedPage("/403.html");//自定义页面；无访问权限

        //7.其他配置
//        http.httpBasic()//默认开启

        //8.返回
        return http.build();
    }

    // 依赖：1. JDBC依赖，2. MySQL依赖，3. 自动注入DataSource
    @Bean
    public JdbcTokenRepositoryImpl repository(DataSource dataSource){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        repository.setCreateTableOnStartup(false);
        return repository;
    }
}
