package com.zking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zking.entity.User;

public interface IUserService extends IService<User>
{
    void transaction(int id1, int id2, int money);

    User register(String username,String password);
//    boolean update(User user);
}
