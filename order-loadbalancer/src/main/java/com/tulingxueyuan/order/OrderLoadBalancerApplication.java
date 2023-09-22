package com.tulingxueyuan.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/***
 * @author guchaolong
 * Spring Cloud LoadBalancer是Spring Cloud官方自己提供的客户端负载均衡器, 用来替代 Ribbon。
 * order-loadbalancer(服务调用方，启动一个)
 * stock-nacos (服务提供方，启动多个)
 *
 * Spring官方提供了两种负载均衡的客户端：RestTemplate、WebClient
 *
 * nacos-discovery中引入了ribbon，需要移除ribbon的包 如果不移除，也可以在yml中配置不使用ribbon
 */
@SpringBootApplication
//@EnableDiscoveryClient
public class OrderLoadBalancerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderLoadBalancerApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        return restTemplate;
    }
}
