package com.zking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//自动生成get/set/toString
@Data
//自动生成全参构造函数
@AllArgsConstructor
//自动生成无参构造函数
@NoArgsConstructor
public class Result<T> {
    private boolean success;
    private String  message;
    private T data;
}
