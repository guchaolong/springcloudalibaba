package com.tulingxueyuan.stock.controller;

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

    @RequestMapping("/reduct")
    public String reduct() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("扣减库存");
        return "扣减库存";
    }

}
