package com.zking.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zking.entity.User;
import com.zking.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

//生成带有必需参数的构造函数，必需的参数是最终字段和具有约束的字段，例如@NonNull ，final
@RequiredArgsConstructor
//shiro的授权以及认证的类
public class MyRealm  extends AuthorizingRealm {


    private final IUserService userService;

    //【授权方法】认证之后才会来这里，授权角色
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取通过验证的主体【对象】
        User principal = (User) principals.getPrimaryPrincipal();
        // 根据用户名查询用户的权限、角色信息，最后授权
        Set<String> roles = new HashSet<>();
        //查询数据库这个用户为什么角色
        String role = userService.getRoleById(principal.getId());
        if (role != null)
        {
            roles.add(role);
        }
        return new SimpleAuthorizationInfo(roles);
    }

    //【认证方法】查询数据库判断是否存在该用户
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户的令牌
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //获取用户令牌的信息
        String username = token.getUsername();
        //根据用户名查询用户是否存在
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        //没有查询到抛出用户不存在的异常
        if (user == null){
            throw new UnknownAccountException();
        }
        //余额低于0抛出用户被冻结的异常
        if (user.getMoney() < 0){
            throw new LockedAccountException();
        }
        //返回的主体信息
        Object principal = user;
        //需要验证的证书（数据库查出来的：密码，用来和用户输入的密码，加密后做比较）
        Object credentials = user.getPassword();
        //加密的盐值
        ByteSource salt =  ByteSource.Util.bytes(user.getUsername());
        //主体的名称
        String realmName = getName();
        //验证密码是否正确（会自动用配置好的加密算法加密）
        return new SimpleAuthenticationInfo(principal,credentials, salt,realmName);
    }
}
