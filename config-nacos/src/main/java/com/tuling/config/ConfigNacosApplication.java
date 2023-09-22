package com.tuling.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

/***
 * @author guchaolong
 * 
 */
@SpringBootApplication
public class ConfigNacosApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ConfigNacosApplication.class, args);
        while (true) {
            String userName = applicationContext.getEnvironment().getProperty("user.name");
            String userAge = applicationContext.getEnvironment().getProperty("user.age");
            String config = applicationContext.getEnvironment().getProperty("user.config");
            System.err.println("user name :" + userName + "; age: " + userAge+";config:"+config);
            TimeUnit.SECONDS.sleep(1);
        }
        // nacos客户端 每10ms去 注册中心进行判断，  根据MD5
    }
}
