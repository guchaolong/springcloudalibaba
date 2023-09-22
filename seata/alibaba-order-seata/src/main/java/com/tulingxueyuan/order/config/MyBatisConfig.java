package com.tulingxueyuan.order.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/***
 * @author guchaolong
 * 
 */
@Configuration
@MapperScan("com.tulingxueyuan.order.mapper")   // 扫描Mapper接口，整合MyBatis
public class MyBatisConfig {

}
