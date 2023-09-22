package com.tulingxueyuan.order.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/***
 * @author guchaolong
 * 
 */
@FeignClient(value="alibaba-stock-seata",path = "/stock")
public interface StockService {


    @RequestMapping("/reduct")
    public String reduct(@RequestParam(value="productId") Integer productId);
}
