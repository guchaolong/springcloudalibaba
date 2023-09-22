package com.tulingxueyuan.order.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

/***
 * @author guchaolong
 * 
 */
@Service
public class OrderSericeImpl implements IOrderService {

    /**
     * 使用了@SentinelResource，就可以把被注解的方法定义为一个资源，可以被 sentinel 识别到
     * 如果配合 sentinel控制台，访问一次之后就能在控制台的"簇点链路"中出现，就可以对齐进行流控
     *
     * 使用了@SentinelResource注解，就不会在用统一异常处理MyBlockExceptionHandler了，所以要加上blockHandler
     *
     * @return
     */
    @Override
    @SentinelResource(value="getUser",blockHandler = "blockHandlerGetUser")
    public String getUser() {
        return "查询用户";
    }

    public String blockHandlerGetUser(BlockException e) {
        return "流控用户";
    }
}
