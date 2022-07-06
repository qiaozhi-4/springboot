package com.zking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zking.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IUserMapper extends BaseMapper<User> {
    //根据giteeid查询用户的信息
    User getOneByGiteeId(String id);

}
