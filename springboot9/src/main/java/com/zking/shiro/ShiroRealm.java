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

@RequiredArgsConstructor
public class ShiroRealm extends AuthorizingRealm {

    private final IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User principal = (User) principals.getPrimaryPrincipal();
        // 权限信息对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if ("admin".equals(principal.getUsername())){
            //授权角色
            info.addRole("admin");//添加角色
        }
        return info ;
    }

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
        //需要验证的证书（数据库查出来的：密码）
        Object credentials = user.getPassword();
        //加密的盐值
        ByteSource salt =  ByteSource.Util.bytes(user.getUsername());
        //主体的名称
        String realmName = getName();
        //验证密码是否正确（会自动用配置好的加密算法加密）
        return new SimpleAuthenticationInfo(principal,credentials, salt,realmName);
    }
}
