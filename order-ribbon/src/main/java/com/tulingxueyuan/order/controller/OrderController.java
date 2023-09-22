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
     * 负载均衡策略：
     * 默认-轮询ribbon
     * 随机-RibbonRandomRuleConfig
     * nacos权重-NacosRuleConfig （在nacos配置服务权重）
     * 自定义-CustomRule
     *
     * 测试接口:http://localhost:8030/order/add
     *
     * @return
     */
    @RequestMapping("/add")
    public String add() {
        System.out.println("下单成功!");

        String msg = restTemplate.getForObject("http://stock-service/stock/reduct", String.class);
        return "Hello World" + msg;
    }
}
