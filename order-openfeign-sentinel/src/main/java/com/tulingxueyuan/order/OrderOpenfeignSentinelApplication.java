package com.tulingxueyuan.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/***
 * @author guchaolong
 * 
 */
@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients
public class OrderOpenfeignSentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderOpenfeignSentinelApplication.class,args);
    }

}
