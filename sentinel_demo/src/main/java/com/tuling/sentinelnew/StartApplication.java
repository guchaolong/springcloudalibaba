package com.tuling.sentinelnew;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/***
 * @author guchaolong
 * sentinel_demo服务（原生的 sentinel 使用方式）
 * 直接编码的方式：http://localhost:8060/hello
 * 注解方式：@SentinelResource、fallback、blockHandler
 * 流控和降级、优先级：http://localhost:8060/user
 * 熔断降级：http://localhost:8060/degrade
 */
@SpringBootApplication
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class,args);
    }

    // 注解支持的配置Bean
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

}
