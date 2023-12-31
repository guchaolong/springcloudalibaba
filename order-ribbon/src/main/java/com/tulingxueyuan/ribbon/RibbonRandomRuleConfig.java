package com.tulingxueyuan.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * @author guchaolong
 * 
 */
@Configuration
public class RibbonRandomRuleConfig {

    // 方法名一定要叫iRule
    @Bean
    public IRule iRule(){
        return new RandomRule();
    }
}
