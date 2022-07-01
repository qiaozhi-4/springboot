package com.zking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zking.entity.User;
import com.zking.mapper.IUserMapper;
import com.zking.service.IUserService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "user")
public class UserService extends ServiceImpl<IUserMapper, User> implements IUserService {
    //  1) 编写根据ID查询指定用户的方法并缓存
    @Override
    //存入redis，如果id小于0或者返回值为空就不存，也不查
    @Cacheable(key = "'id::' + #id", unless  = "#id < 0 || #result == null")
    public User findById(int id) {
        System.out.println("我查询了一个用户");
        return getById(id);
    }

    //	2) 编写分页查询，指定页码和每页数量，缓存到Redis（key可以为：数量#页码）
    @Override
    //存入redis，如果pageNum小于0或者返回值为空就不存
    @Cacheable(key = "'page::' + #pageNum", unless  = "#pageNum < 0 || #result == null")
    public PageInfo<User> findPage(int pageNum, int pageSize) {
        System.out.println("我查询了分页");
        PageHelper.startPage(pageNum, pageSize);//使用分页，查询第【pageNum】页，每页【pageSize】条
        return new PageInfo<>(list());
    }

    //  3) 编写根据方法插入新的用户并缓存，同时清空分页缓存数据
    @Override
    //存入redis，如果返回值为空就不存
    @CachePut(key = "'id::' + #user.id", unless = "#result == null")
    //删除redis的分页缓存信息
    @CacheEvict(cacheNames = "user::page", allEntries = true)
    public User addUser(User user) {
        System.out.println("我插入了数据");
        save(user);
        return user.getId() != null ? user : null;
    }

    //	4) 编写根据ID更新用户的方法，并清空分页缓存数据
    @Override
    //存入redis，如果返回值为空就不存，也不查
    @CachePut(key = "'id::' + #user.id", unless = "#result == null")
    //删除redis的分页缓存信息
    @CacheEvict(cacheNames = "user::page", allEntries = true)
    //开启事务
    @Transactional
    public User updateByIdUser(User user) {
        System.out.println("我更新了数据库");
        return updateById(user) ? user : null;
    }
}
