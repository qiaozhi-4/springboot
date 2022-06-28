package com.zking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zking.entity.Order;
import com.zking.mapper.IOrderMapper;
import com.zking.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService extends ServiceImpl<IOrderMapper, Order> implements IOrderService {

    //查询所有订单
    @Override
    public PageInfo<Order> findAll(Integer pageNum) {
        if (pageNum == null){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,5);//使用分页，查询第二页，每页5条
        PageInfo<Order> pageOrder = new PageInfo<>(list());
        return pageOrder;
    }

    //模糊查询
    @Override
    public PageInfo<Order> findByFuzzy(String str,Integer pageNum) {
        if (pageNum == null){
            pageNum = 1;
        }
        if (str == null){
            return findAll(pageNum);
        }
        str = "%" + str + "%";
//        PageHelper.startPage(pageNum,5);//使用分页，查询第二页，每页5条
        PageInfo<Order> pageOrder = new PageInfo<>(list(new QueryWrapper<Order>()
                .like("info", str)
                .or().like("address", str)));
        return pageOrder;
    }

    //订单修改功能
    @Override
    public boolean updateOrderById(Order order) {
        return updateById(order);
    }

    //订单添加功能
    @Override
    public Order addOrder(Order order) {
        if (save(order)){
            return order;
        }
        return null;
    }

    //订单删除功能
    @Override
    public boolean deleteOrderById(int id) {
        return removeById(id);
    }







}
