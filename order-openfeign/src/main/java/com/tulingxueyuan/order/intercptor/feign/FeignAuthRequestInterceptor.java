package com.tulingxueyuan.order.intercptor.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Description:
 *
 * @author Ezekiel
 * @date 2023/9/21 02:36
 */
public class FeignAuthRequestInterceptor implements RequestInterceptor {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void apply(RequestTemplate requestTemplate) {

        // 业务逻辑
        String access_token = UUID.randomUUID().toString();
        requestTemplate.header("Authorization", access_token);

        logger.info("feign拦截器-FeignAuthRequestInterceptor！");
    }
}
