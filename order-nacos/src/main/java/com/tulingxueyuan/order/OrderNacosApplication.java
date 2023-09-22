package com.tulingxueyuan.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/***
 * @author guchaolong
 *
 */
@SpringBootApplication
//@EnableDiscoveryClient //这个注解在某个版本之后，可以不加了
public class OrderNacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderNacosApplication.class, args);
    }

    @Bean
    @LoadBalanced//restTemplate要依赖负载均衡器，所以要加注解@LoadBalanced，不然会返回Whitelabel Error Page
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        return restTemplate;
    }
}
