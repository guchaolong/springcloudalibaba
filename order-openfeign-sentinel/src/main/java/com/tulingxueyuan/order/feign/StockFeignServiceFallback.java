package com.tulingxueyuan.order.feign;

import org.springframework.stereotype.Component;

/***
 * @author guchaolong
 * 
 */
@Component
public class StockFeignServiceFallback implements StockFeignService {
    @Override
    public String reduct2() {
        return "降级啦！！！";
    }
}
