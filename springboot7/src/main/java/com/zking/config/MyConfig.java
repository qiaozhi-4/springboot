package com.zking.config;

import com.zking.interceptor.MyInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MyConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns("/**")// 拦截路径 url-pattern
                .excludePathPatterns("/t1")//排除路径 exclude-url-pattern
                .order(1);//拦截器的顺序（优先级）
    }
}
