package com.tulingxueyuan.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/***
 * @author guchaolong
 * 
 */
@SpringBootApplication
@EnableTransactionManagement     // 开启本地事务@Transactional
public class OrderSeataApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderSeataApplication.class,args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        RestTemplate restTemplate = builder.build();
        return restTemplate;
    }
}
