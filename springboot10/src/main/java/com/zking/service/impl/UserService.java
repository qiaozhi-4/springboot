package com.zking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zking.entity.User;
import com.zking.mapper.IUserMapper;
import com.zking.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<IUserMapper, User> implements IUserService {

}
