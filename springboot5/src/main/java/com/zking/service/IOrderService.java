package com.zking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zking.entity.Order;

import java.util.List;

public interface IOrderService extends IService<Order> {
    //查询所有订单
    PageInfo<Order> findAll(Integer pageNum);
    //模糊查询
    PageInfo<Order> findByFuzzy(String str,Integer pageNum);
    //订单修改功能
    boolean updateOrderById(Order order);
    //订单添加功能
    Order addOrder(Order order);
    //订单删除功能
    boolean deleteOrderById(int id);
}
