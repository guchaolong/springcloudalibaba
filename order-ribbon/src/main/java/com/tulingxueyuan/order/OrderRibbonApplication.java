package com.tulingxueyuan.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/***
 * @author guchaolong
 * 负载均衡 ribbon
 * order-ribbon(服务调用方，启动一个)
 * stock-nacos (服务提供方，启动多个)
 */
@SpringBootApplication
//@EnableDiscoveryClient
/*@RibbonClients(value = {
        @RibbonClient(name="stock-service",configuration = RibbonRandomRuleConfig.class)
})*/
public class OrderRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderRibbonApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        return restTemplate;
    }
}
