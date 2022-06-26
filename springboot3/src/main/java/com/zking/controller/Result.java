package com.zking.controller;

public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    //成功
    public static Result success(String reason){
        return null;
    }

    //失败
    public static Result fail(String reason){
        return null;
    }
}
