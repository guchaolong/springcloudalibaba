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
     * 测试接口：http://localhost:8031/order/add
     * @return
     */
    @RequestMapping("/add")
    public String add() {
        System.out.println("下单成功!");

        String msg = restTemplate.getForObject("http://stock-service/stock/reduct", String.class);

        //String msg = stockService.reduct();
        return "Hello World" + msg;
    }
}
