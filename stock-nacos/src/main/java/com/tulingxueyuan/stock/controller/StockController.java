package com.tulingxueyuan.stock.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/***
 * @author guchaolong
 *
 */
@RestController
@RequestMapping("/stock")
public class StockController {


    @Value("${server.port}")
    String port;

    @RequestMapping("/reduct")
    public String reduct() throws InterruptedException {
        System.out.println("扣减库存");
        return "扣减库存:" + port;//加上端口，方便看出调的哪个端口的实例
    }


    @RequestMapping("/reduct2")
    public String reduct2() {
        int a = 1 / 0;
        System.out.println("扣减库存");
        return "扣减库存:" + port;
    }

}
