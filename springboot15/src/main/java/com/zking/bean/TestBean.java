package com.zking.bean;

import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

public class TestBean implements InstantiationAwareBeanPostProcessor {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}