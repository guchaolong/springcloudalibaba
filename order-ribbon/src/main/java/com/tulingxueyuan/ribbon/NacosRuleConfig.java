package com.tulingxueyuan.ribbon;

import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author Ezekiel
 * @date 2023/9/20 22:52
 */
@Configuration
public class NacosRuleConfig {
    /**
     * 全局配置
     * 指定负载均衡策略
     * @return
     */
    @Bean
    public IRule iRule() {
        // 指定使用Nacos提供的负载均衡策略（优先调用同一集群的实例，基于随机权重）
        return new NacosRule();
    }
}
