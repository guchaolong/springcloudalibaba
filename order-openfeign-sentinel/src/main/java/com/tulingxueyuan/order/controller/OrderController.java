package com.tulingxueyuan.order.controller;

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

    @Autowired
    StockFeignService stockFeignService;

    /**
     *
     * 之前的 order-openfeign使用了FeignClient调用其他服务，那如果服务提供者出现异常，就会把异常带过来，
     * 调用方怎么处理呢？就是 openfeign 整合 sentinel，然后使用降级处理
     *
     * 即 openfeign 整合 sentinel
     * 1.添加openfeign依赖
     * 2.配置文件 feign.sentinel.enabled: true
     * 3.openfeign 接口 StockFeignService
     * 4.openfeign 的 fallback 实现类 StockFeignServiceFallback（必须实现StockFeignService）
     *
     * 测试 http://localhost:8041/order/add
     *
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        System.out.println("下单成功!");
        String s = stockFeignService.reduct2();
        return "Hello Feign"+s;
    }
}
