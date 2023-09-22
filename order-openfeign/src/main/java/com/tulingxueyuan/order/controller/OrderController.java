package com.tulingxueyuan.order.controller;

import com.tulingxueyuan.order.feign.ProductFeignService;
import com.tulingxueyuan.order.feign.StockFeignService;
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

    /**
     * 3.通过
     */
    @Autowired
    StockFeignService stockFeignService;

    @Autowired
    ProductFeignService productFeignService;


    /**
     * 测试接口：http://localhost:8040/order/add1
     * 集成了 ribbon，所以默认会轮询的负载策略
     * @return
     */
    @RequestMapping("/add1")
    public String add1() {
        System.out.println("下单成功!");
        String s = stockFeignService.reduct();
        return "Hello Feign" + s;
    }

    /**
     * 同时调用了两个远程服务
     *
     * 测试接口：http://localhost:8040/order/add
     *
     * 日志配置（查看日志的区别）
     * 契约配置
     * 超时配置 (修改配置文件中的超时readTimeout: 3000, product-nacos中休眠 4000)
     * feign拦截器(查看调 product-service的时候，url是否变化了)
     * @return
     */
    @RequestMapping("/add")
    public String add() {
        System.out.println("下单成功!");
        String s = stockFeignService.reduct();
        String p = productFeignService.get(1);
        return "Hello Feign" + s + p;
    }
}
