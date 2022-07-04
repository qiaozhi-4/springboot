package com.zking.shiro;

import com.zking.service.IUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

//注解为配置类
@Configuration
//注解，开启读取yml里面的属性，直接添加类属性，属性名不同，就给属性添加@Value("填yml的属性名")注解
@ConfigurationProperties(prefix = "shiro")
//自动生成get/set/toString
@Data
//生成带有必需参数的构造函数，必需的参数是最终字段和具有约束的字段，例如@NonNull ，final
@RequiredArgsConstructor
//配置shiro所需要的bean
public class ShiroConfig {

    //获取yml配置文件里面的属性
    private LinkedHashMap<String, String> chain;
    private String hashAlgorithmName;
    private int hashIterations;

    private final IUserService userService;

    //注解配置shiro所需bean
    @Bean
    public Realm realm() {
        AuthorizingRealm realm = new MyRealm(userService);
        //开启缓存
        realm.setCachingEnabled(true);
        //配置加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(hashAlgorithmName);
        matcher.setHashIterations(hashIterations);
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    //注解配置shiro所需bean
    @Bean
    public ShiroFilterChainDefinition chainDefinition() {
        //配置页面所需要的角色，和权限（可以不配置，直接在控制器使用注解配置）
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinitions(chain);
        return definition;
    }

}
