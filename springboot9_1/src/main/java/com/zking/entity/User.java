package com.zking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//自动生成get/set/toString
@Data
//自动生成全参构造函数
@AllArgsConstructor
//自动生成无参构造函数
@NoArgsConstructor
//@TableName("user2")
public class User implements Serializable {
//    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private Double money;
}

