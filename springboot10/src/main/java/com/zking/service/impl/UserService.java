package com.zking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zking.entity.User;
import com.zking.mapper.IUserMapper;
import com.zking.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//生成带有必需参数的构造函数，必需的参数是最终字段和具有约束的字段，例如@NonNull ，final
@RequiredArgsConstructor
@Service
public class UserService extends ServiceImpl<IUserMapper, User> implements IUserService {

    private final IUserMapper userMapper;


    @Override
    public String getRoleById(int id) {
        return userMapper.getRoleById(id);
    }
}
