package com.tulingxueyuan.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/***
 * @author guchaolong
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    RestTemplate restTemplate;

    /**
     * order 订单服务(服务调用方)
     * stock 库存服务(服务提供方)
     * <p>
     * 未使用注册中心之前，可以用restTemplate调其他服务
     * 通过硬编码的方式，写死了端口等信息，如果服务提供者发生变化，这里也需要改，很麻烦
     * <p>
     * 测试接口 http://localhost:8010/order/add
     *
     * @return
     */
    @RequestMapping("/add")
    public String add() {
        System.out.println("下单成功!");
        String msg = restTemplate.getForObject("http://localhost:8011/stock/reduct", String.class);
        return "Hello World " + msg;
    }
}
