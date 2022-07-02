package com.zking.shiro;

import lombok.Data;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
@ConfigurationProperties(prefix = "shiro")
@Data
public class ShiroConfig {

    private LinkedHashMap<String, String> chain;
    private String loginUrl;
    private String successUrl;
    private String unauthorizedUrl;
    private String hashAlgorithmName;
    private int hashIterations;

    @Bean
    public ShiroRealm realm(){
        ShiroRealm realm = new ShiroRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(hashAlgorithmName);
        matcher.setHashIterations(hashIterations);
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

//    @Bean/*("securityManager")*/
//    public SecurityManager securityManager(Realm realm){
//        DefaultWebSecurityManager manager = new DefaultWebSecurityManager(realm);
//        return manager;
//    }

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
