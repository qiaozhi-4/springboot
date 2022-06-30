package com.zking.exception;

public class MyException extends RuntimeException{
    public MyException() {
    }
    public MyException(String msg) {
        super(msg);
    }
}
