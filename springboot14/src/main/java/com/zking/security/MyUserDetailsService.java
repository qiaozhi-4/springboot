package com.zking.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zking.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//用于数据库就验证以及加密
@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final PasswordEncoder encoder;
    private final IUserService userService;

    //用户登录验证方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.trim().isEmpty()) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        //查询数据库
        com.zking.entity.User user = userService.getOne(
                new QueryWrapper<com.zking.entity.User>().eq("username", username));
        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        String name = user.getUsername();
        String password = user.getPassword();
        StringBuilder authorities = new StringBuilder();
        //查询用户的角色
        userService.getRolesByUserId(user.getId())
                .forEach(role -> {
                    assert false;
                    authorities.append("ROLE_").append(role.getName()).append(",");
                });
        //查询用户的权限
        userService.getAuthoritySByUserId(user.getId())
                .forEach(authority -> {
                    assert false;
                    authorities.append(authority.getName()).append(",");
                });
        // 返回User对象，这里的User对象是Spring自带的类
        return new User(
                name, password, AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(authorities)));
    }
}
