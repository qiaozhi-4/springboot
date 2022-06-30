package com.zking.service;

import com.zking.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
//表示user是这个类中共用的缓存名字,方法的注解可以不写【cacheNames = "user"】
@CacheConfig(cacheNames = "user")
public class MyService {


    private final RedisTemplate<String, Object> redisTemplate;

    public List<User> findAll() {
        //绑定这个key
        BoundValueOperations<String, Object> ops = redisTemplate.boundValueOps("user::all");
        List<User> users = (List<User>) ops.get();
        if (users == null) {
            User user1 = new User(1, "kk", "123", 100.0);
            User user2 = new User(2, "bb", "123", 100.0);
            User user3 = new User(3, "ff", "123", 100.0);
            User user4 = new User(4, "gg", "123", 100.0);
            users = Arrays.asList(user1, user2, user3, user4);
            ops.set(users);
        }

        return users;
    }

    //【cacheNames】+【key】就是存入redis时的key，这里是user::id::1
    //【condition】条件，和【unless】相反
    //【unless】条件，如果id为空就不存入redis，
    // 【#result】springboot自带的结果集
    @Cacheable(cacheNames = "user", key = "'id::' + #id", unless = "#id == null || #result == null ")
    public User findById(int id) {
        User user1 = new User(1, "kk", "123", 100.0);
        return user1;
    }

    @Cacheable(key = "'name::' + #name", unless = "#name == null || #result == null ")
    public User findByName(String name) {
        User user1 = new User(1, "kk", "123", 100.0);
        return user1;
    }

    //删除redis缓存
    @CacheEvict(allEntries = true, condition = "#result")
    public boolean deleteById(int id) {
        System.out.println("删除id" + id);
        return id > 0;
    }

    @CacheEvict(key = "'id::' + #id")
    public void addUser(int id) {
        System.out.println("删除id" + id);
    }
}
