package com.tulingxueyuan.order.service.impl;

import com.tulingxueyuan.order.api.StockService;
import com.tulingxueyuan.order.mapper.OrderMapper;
import com.tulingxueyuan.order.pojo.Order;
import com.tulingxueyuan.order.service.OrderService;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/***
 * @author guchaolong
 * 
 */
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    OrderMapper orderMapper;

    @Autowired
    StockService stockService;

    /**
     * 下单
     * @return
     */
    @GlobalTransactional
    @Override
    public Order create(Order order) {
        // 插入能否成功？
        orderMapper.insert(order);

        // 扣减库存 能否成功？
        stockService.reduct(order.getProductId());

        // 异常
        int a=1/0;

        return order;
    }

    @Override
    @Trace
    @Tag(key="getAll",value="returnedObj")
    public List<Order> all() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return orderMapper.selectAll();
    }

    @Override
    @Trace
    @Tags({@Tag(key="getAll",value="returnedObj"),
            @Tag(key="getAll",value="arg[0]")})
    public Order get(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }
}
