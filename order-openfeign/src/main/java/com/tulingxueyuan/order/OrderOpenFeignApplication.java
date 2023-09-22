package com.tulingxueyuan.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/***
 * @author guchaolong
 * order-openfeign(服务调用方，启动一个)
 * stock-nacos (服务提供方，启动多个)
 * product-nacos (服务提供方，启动多个)
 *
 * 1、添加openfeign依赖
 * 2、添加feign接口和方法，@FeignClient
 * 3、通过新的接口调用（不再使用 RestTemplate 了）
 * 4、@EnableFeignClients
 */
@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients//4. 添加这个注解
public class OrderOpenFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderOpenFeignApplication.class,args);
    }

}
