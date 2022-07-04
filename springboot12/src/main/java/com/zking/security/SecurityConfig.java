package com.zking.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(encoder.encode("123"))
//                .roles("admin", "user")
//                .authorities("insert", "delete", "/admin")
//                .and()
//                .withUser("qz")
//                .password(encoder.encode("123"))
//                .authorities("user");
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

//    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login.html") // 登录的页面
                .loginProcessingUrl("/login") // 处理登录的请求链接
                .defaultSuccessUrl("/") // 成功登录页面
                .permitAll()
                .and()
                .logout()              // 登出配置
                .logoutUrl("/logout")  // 登出的URL
                .logoutSuccessUrl("/login.html") // 登出成功后的URL
                .and()
                .authorizeRequests()
                // 指定角色
                // .antMatchers("/test1").hasRole("user")
                // .antMatchers("/test1").hasRole("admin")
                .antMatchers("/t2").hasAuthority("user")
                // 指定权限
                .antMatchers("/t3").hasAuthority("delete")
                // 任意一个权限即可
                .antMatchers("/t4").hasAnyAuthority("insert", "delete")
                // 所有其他请求允许访问
                // .anyRequest().permitAll()
                // 关闭CSRF
                .and().csrf().disable();
    }
}
