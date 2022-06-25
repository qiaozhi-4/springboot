package com.zking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zking.entity.User;
import com.zking.mapper.IUserMapper;
import com.zking.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends ServiceImpl<IUserMapper, User> implements IUserService
{
    @Transactional
    @Override
    public void transaction(int id1, int id2, int money)
    {
        // 获取
        User user1 = getById(id1);
        User user2 = getById(id2);
        if (user1 == null || user2 == null)
        {
            System.out.println("无此账户！");
            return;
        }
        
        // 修改
        user1.setMoney(user1.getMoney() - money);
        user2.setMoney(user2.getMoney() + money);
        
        // 更新
        updateById(user1);
//        System.out.println(1 / 0); // 异常
        updateById(user2);
        System.out.println("转账成功！");
    }

    //注册
    @Override
    public User register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if (save(user)){
            return user;
        }
        return null;
    }

    //注册
//    @Override
//    public boolean update(User user) {
//        return update(user);
//    }


}
