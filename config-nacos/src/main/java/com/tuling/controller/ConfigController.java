package com.tuling.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author guchaolong
 * 
 *
 * 通过@Value的方式获取配置，如果不加@RefreshScope 注解，是无法动态获取到的，必须要加，才能获取到
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${user.name}")
    public String name;

    @RequestMapping("/show")
    public String show(){
        return name;
    }
}
