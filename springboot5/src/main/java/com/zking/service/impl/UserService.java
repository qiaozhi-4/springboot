package com.zking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zking.entity.User;
import com.zking.mapper.IUserMapper;
import com.zking.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends ServiceImpl<IUserMapper, User> implements IUserService
{

    //注册
    @Override
    public User register(String username, String password) {
        if (username.isEmpty() || password.isEmpty()){
            return null;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setMoney(0.0);
        if (save(user)){
            return user;
        }
        return null;
    }

    //登录
    @Override
    public User login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()){
            return null;
        }
        return getOne(new QueryWrapper<User>().eq("username",username).eq("password",password));
    }
}
