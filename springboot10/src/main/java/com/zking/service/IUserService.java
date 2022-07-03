package com.zking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zking.entity.User;

public interface IUserService extends IService<User>{
    String getRoleById(int id);
}
