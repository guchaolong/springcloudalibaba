package com.tulingxueyuan.order.controller;

import com.tulingxueyuan.order.pojo.Order;
import com.tulingxueyuan.order.service.OrderService;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 * @author guchaolong
 * 
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    // 插入订单信息
    @RequestMapping("/add")
    public String add(){
        Order order=new Order();
        order.setProductId(9);
        order.setStatus(0);
        order.setTotalAmount(100);

        orderService.create(order);
        return "下单成功";
    }

    // 获取单个订单信息
    @RequestMapping("/get/{id}")
    public Order get(@PathVariable Integer id){

        return orderService.get(id);
    }


    @RequestMapping("/all")
    public List<Order> getAll() throws InterruptedException {

        return orderService.all();
    }
}
