package com.tulingxueyuan.order.intercptor.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/***
 * @author guchaolong
 * 
 *
 * 自定义拦截器实现认证逻辑
 */
public class CustomFeignInterceptor implements RequestInterceptor {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // Todo
        requestTemplate.header("xxx","xxx");
        requestTemplate.query("id","111");
        requestTemplate.uri("/9");

        logger.info("feign拦截器-CustomFeignInterceptor！");
    }
}
