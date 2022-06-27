package com.zking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zking.entity.User;

public interface IUserService extends IService<User>
{
    //注册
    User register(String username, String password);
    //登录
    User login(String username, String password);
}
