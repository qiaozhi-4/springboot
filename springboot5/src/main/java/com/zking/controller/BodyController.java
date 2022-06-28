package com.zking.controller;

import com.github.pagehelper.PageInfo;
import com.zking.entity.Order;
import com.zking.entity.User;
import com.zking.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// @RestController = @ResponseBody + @Controller
@RestController
//自动生成无参带参构造
@RequiredArgsConstructor
//一级路径
@RequestMapping

//标注共享的属性名
@SessionAttributes({"user"})
public class BodyController {

    private final IOrderService orderService;

    //获取所有订单
    @GetMapping("getOrders")
    public PageInfo<Order> getOrders(Integer pageNum) {
        return orderService.findAll(pageNum);
    }

    //模糊查询订单
    @GetMapping("fuzzyOrders")
    public PageInfo<Order> fuzzyOrders(String str, Integer pageNum) {
        return orderService.findByFuzzy(str,pageNum);
    }

    //更新订单
    @PutMapping("updateOrder")
    public boolean updateOrder(Order order) {
        return orderService.updateOrderById(order);
    }

    //添加订单
    @PostMapping("addOrder")
    public Order addOrder(Order order, @ModelAttribute("user") User user) {
        order.setUserId(user.getId());
        return orderService.addOrder(order);
    }

    //删除订单
    @DeleteMapping("deleteOrder")
    public boolean deleteOrder(Integer id) {
        if (id == null){
            return false;
        }
        return orderService.deleteOrderById(id);
    }


}
