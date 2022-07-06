package com.zking.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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

    @Bean
    public WebSecurityCustomizer customizer(){
        //可以配置被验证拦截忽略的web资源
        return web -> web.ignoring()
                .antMatchers("/img/**","/css/**","/js/**");
    }

    //核心
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        自定义登录登出页面
        httpSecurity.formLogin()
                .loginPage("/login.html")  //自定义的登录页面
                .loginProcessingUrl("/login")   //内置的处理请求路径
                .failureUrl("/login.html?error")
                .defaultSuccessUrl("/success.html") //自定义成功后转发到页面
        ;
//        自定义登出表单
        httpSecurity.logout()
                .logoutUrl("/logout")   //内置处理请求
                .logoutSuccessUrl("/login.html?logout")    //自定义登出成功，和下面的二选一
        ;
//        所有的请求都必须认证：如果这里设置了，注解就会覆盖
//        登录权限异常处理
        httpSecurity.exceptionHandling()
                .accessDeniedPage("/403.html") //自定义错误页面
        ;
        return httpSecurity.build();
    }

    // 依赖：1. JDBC依赖，2. MySQL依赖，3. 自动注入DataSource
//    @Bean
//    public JdbcTokenRepositoryImpl repository(DataSource dataSource) {
//        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
//        repository.setDataSource(dataSource);
//        repository.setCreateTableOnStartup(false);
//        return repository;
//    }

    //监听器
    @EventListener
    public void loginSuccess(AuthenticationSuccessEvent event){
        System.out.println("登录成功：" + event.getAuthentication());
    }
    @EventListener
    public void loginFail(AbstractAuthenticationFailureEvent event){
        System.out.println("登录失败：" + event.getException());
    }
}
