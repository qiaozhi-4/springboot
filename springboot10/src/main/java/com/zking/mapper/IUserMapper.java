package com.zking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zking.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMapper extends BaseMapper<User> {

}
