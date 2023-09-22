package com.tulingxueyuan.order.service;

import com.tulingxueyuan.order.pojo.Order;

import java.util.List;
import java.util.concurrent.TimeUnit;

/***
 * @author guchaolong
 * 
 */
public interface OrderService {

     Order create(Order order);

    List<Order> all() throws InterruptedException;

    Order get(Integer id);
}
