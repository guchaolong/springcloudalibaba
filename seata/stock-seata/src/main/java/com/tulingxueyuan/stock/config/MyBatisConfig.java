package com.tulingxueyuan.stock.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/***
 * @author guchaolong
 * 
 */
@Configuration
@MapperScan("com.tulingxueyuan.stock.mapper")
public class MyBatisConfig {

}
