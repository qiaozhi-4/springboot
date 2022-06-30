package com.zking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zking.entity.User;

public interface IUserService extends IService<User>{
//  1) 编写根据ID查询指定用户的方法并缓存
    User findById(int id);
//	2) 编写分页查询，指定页码和每页数量，缓存到Redis（key可以为：数量#页码）
    PageInfo<User> findPage(int pageNum, int pageSize);
//  3) 编写根据方法插入新的用户并缓存，同时清空分页缓存数据
    User addUser(User user);
//	4) 编写根据ID更新用户的方法，并清空分页缓存数据
    User updateByIdUser(User user);
}
