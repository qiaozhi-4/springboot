package com.zking.shiro;

import com.zking.service.IUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
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
    private String loginUrl;
    private String successUrl;
    private String unauthorizedUrl;
    private String hashAlgorithmName;
    private int hashIterations;

    private final IUserService userService;

    //构建自己实现的shiro授权以及认证的类
    @Bean
    public MyRealm realm(){
        MyRealm realm = new MyRealm(userService);

        //配置MD5加密：名称
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(hashAlgorithmName);
        //配置MD5加密：迭代次数
        matcher.setHashIterations(hashIterations);
        realm.setCredentialsMatcher(matcher);

        //redis缓存相关，具体配置在ehcache.xml里面
        //开启授权缓存
        realm.setAuthorizationCachingEnabled(true);
        //开启身份验证缓存
        realm.setAuthenticationCachingEnabled(true);
        //设置授权缓存
        realm.setAuthorizationCacheName("authorization");
        //设置身份验证缓存
        realm.setAuthenticationCacheName("authentication");
        return realm;
    }

    //缓存管理配置
    @Bean
    public CacheManager cacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        //读取缓存管理器配置文件
        cacheManager.setCacheManagerConfigFile("classpath:shiro/ehcache.xml");
        return cacheManager;
    }

    //配置shiro管理器
    @Bean
    public SecurityManager securityManager(Realm realm, CacheManager cacheManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager(realm);

        //使用redis缓存管理
        manager.setCacheManager(cacheManager);
        return manager;
    }

    //配置shiro管理器工厂
    @Bean
    public ShiroFilterFactoryBean filterFactoryBeanOld(SecurityManager manager){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        //给工厂配置shiro管理器（也就是模型）
        filterFactoryBean.setSecurityManager(manager);
        //读取yml配置里面的map【配置请求路径所需要的权限以及角色】
        filterFactoryBean.setFilterChainDefinitionMap(chain);
        //读取yml配置里面的登录路径
        filterFactoryBean.setLoginUrl(loginUrl);
        //读取yml配置里面的登录成功
        filterFactoryBean.setSuccessUrl(successUrl);
        //读取yml配置里面的权限不足路径
        filterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        return filterFactoryBean;
    }
}
