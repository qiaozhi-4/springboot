package com.zking.shiro;

import com.zking.service.IUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
@ConfigurationProperties(prefix = "shiro")
@Data
@RequiredArgsConstructor
public class ShiroConfig {


    private final IUserService userService;

    private LinkedHashMap<String, String> chain;
    private String loginUrl;
    private String successUrl;
    private String unauthorizedUrl;
    private String hashAlgorithmName;
    private int hashIterations;

    @Bean
    public ShiroRealm realm(){
        ShiroRealm realm = new ShiroRealm(userService);
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(hashAlgorithmName);
        matcher.setHashIterations(hashIterations);
        realm.setCredentialsMatcher(matcher);

        //配置缓存管理的名字
        realm.setAuthorizationCachingEnabled(true);
        realm.setAuthenticationCachingEnabled(true);
        realm.setAuthorizationCacheName("authorization");
        realm.setAuthenticationCacheName("authentication");
        return realm;
    }

    //缓存管理
    @Bean
    public CacheManager cacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:shiro/ehcache.xml");
        return cacheManager;
    }

    //开启shiro注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor advisor(SecurityManager manager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator creator(SecurityManager manager){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        return creator;
    }

    @Bean
    public SecurityManager securityManager(Realm realm,CacheManager cacheManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager(realm);
//            manager.setSessionManager(); 使用redis缓存
        manager.setCacheManager(cacheManager);
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean filterFactoryBeanOld(SecurityManager manager){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(manager);
        filterFactoryBean.setFilterChainDefinitionMap(chain);
        filterFactoryBean.setLoginUrl(loginUrl);
        filterFactoryBean.setSuccessUrl(successUrl);
        filterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        return filterFactoryBean;
    }
}
