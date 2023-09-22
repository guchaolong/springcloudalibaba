package com.tulingxueyuan.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

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
     * 有了注册中心后，通过服务名就能调用其他服务了
     * 这里的 restTemplate要依赖负载均衡器，所以要加注解@LoadBalanced，不然会返回Whitelabel Error Page
     *
     * 启动一个order-nacos服务，3 个 stock-nacos服务（3个端口）
     * 测试接口：http://localhost:8020/order/add
     * 默认是轮询的方式，注册中心看服务列表等信息
     */
    @RequestMapping("/add")
    public String add() {
        System.out.println("下单成功!");
        String msg = restTemplate.getForObject("http://stock-service/stock/reduct", String.class);
        return "Hello World" + msg;
    }


    @RequestMapping("/header")
    public String header(@RequestHeader("X-Request-color") String color) {
        return color;
    }


    @RequestMapping("/flow")
    //@SentinelResource(value = "flow",blockHandler = "flowBlockHandler")
    public String flow() throws InterruptedException {
        return "正常访问";
    }


    @RequestMapping("/flowThread")
    //@SentinelResource(value = "flowThread",blockHandler = "flowBlockHandler")
    public String flowThread() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("正常访问");
        return "正常访问";
    }


    // 关联流控  访问/add 触发/get
    @RequestMapping("/get")
    public String get() throws InterruptedException {
        return "查询订单";
    }


    @RequestMapping("/err")
    public String err() {
        int a = 1 / 0;
        return "hello";
    }


    /**
     * 热点规则，必须使用@SentinelResource
     *
     * @param id
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/get/{id}")
    public String getById(@PathVariable("id") Integer id) throws InterruptedException {

        System.out.println("正常访问");
        return "正常访问";
    }


}
