package com.tulingxueyuan.order.feign;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * @author guchaolong
 * 
 */
@FeignClient(name="product-service",path ="/product")
public interface ProductFeignService {


    /**
     * @RequestLine 是Feign的原生注解
     * 配置文件中contract: feign.Contract.Default #设置为默认的契约  （还原成原生注解）
     * 就需要把RequestMapping注解换成原生注解RequestLine，不然会报错
     * 老版本中要注意这种情况，新版本中是 OpenFeign,使用 Springmvc的RequestMapping 就可以了
     * @param id
     * @return
     */
//    @RequestMapping("GET /{id}")
    @RequestLine("GET /{id}")     //  @RequestLine
    public String get(@Param("id") Integer id);  // @Param
}
