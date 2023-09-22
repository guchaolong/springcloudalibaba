package com.tulingxueyuan.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 * @author guchaolong
 * 
 */
@SpringBootApplication
public class StockSeataApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockSeataApplication.class,args);
    }
}
