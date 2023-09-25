Spring Cloud Alibaba学习 demo
视频地址：https://www.bilibili.com/video/BV1fe4y1b7ha?p=13&spm_id_from=pageDriver&vd_source=7cd131d39951a0f75ccd01c6da135735

springcloudalibaba:整个父模块

# 无微服务的时候，服务调用
order 订单服务(服务调用方)
stock 库存服务(服务提供方)

知识点：
restTemplate
硬编码写死了 stock 服务的 IP 端口
测试接口：http://localhost:8010/order/add


# 加入注册中心nacos
order-nacos 加注册中心的订单服务(服务调用方)
stock-nacos 加注册中心的库存服务(服务提供方)

知识点：
注册中心
启动：
cd /Users/ezekiel/DevTool/nacos 、bin/startup.sh
地址：http://192.168.10.2:8848/nacos/

@LoadBalanced

启动一个OrderNacos服务，3 个 StockNacos服务（3个端口）
测试接口：http://localhost:8020/order/add
默认是轮询的方式，注册中心看服务列表等信息


# 负载均衡 ribbon 
order-ribbon(服务调用方，启动一个)
stock-nacos (服务提供方，启动多个)
两种写法

配置类+@RibbonClient的方式 （有个坑，配置类不能在@CompentScan扫描得到的地方）
配置类+配置文件（推荐）

负载均衡策略：
默认-轮询ribbon
随机-RibbonRandomRuleConfig
nacos权重-NacosRuleConfig （在nacos配置服务权重）
自定义-CustomRule
测试接口:http://localhost:8030/order/add

# Spring Cloud LoadBalancer
是Spring Cloud官方自己提供的客户端负载均衡器, 用来替代 Ribbon。
order-loadbalancer(服务调用方，启动一个)
stock-nacos (服务提供方，启动多个)
nacos-discovery中引入了ribbon，需要移除ribbon的包 如果不移除，也可以在yml中配置不使用ribbon
测试接口：http://localhost:8031/order/add

# OpenFeign
order-openfeign(服务调用方，启动一个)
stock-nacos (服务提供方，启动多个)
product-nacos (服务提供方，启动多个)
1、添加openfeign依赖
2、添加feign接口和方法，@FeignClient
3、通过新的接口调用（不再使用 RestTemplate 了）
4、@EnableFeignClients
测试接口：http://localhost:8040/order/add1
集成了 ribbon，所以默认会轮询的负载策略

测试接口：http://localhost:8040/order/add
日志配置
契约配置
超时配置 (修改order-openfeign配置文件中的超时readTimeout: 3000,product-nacos中休眠 4000)
feign拦截器(查看调 product-service的时候，url是否变化了)


# nacos config


# sentinel
sentinel_demo服务（原生的 sentinel 使用方式）
直接编码的方式：http://localhost:8060/hello

注解方式：@SentinelResource、fallback、blockHandler
流控和降级、优先级：http://localhost:8060/user
熔断降级：http://localhost:8060/degrade


order-sentinel服务 （整合 sentinel控制台）
启动控制台：
cd DevTool/sentinel-dashboard
java -Dserver.port=8858 -jar sentinel-dashboard-1.8.5.jar
或直接java -Dserver.port=8858 -jar DevTool/sentinel-dashboard/sentinel-dashboard-1.8.5.jar
访问：http://localhost:8858/
用户名密码默认都是 sentinel

启动项目order-sentinel之后，刷新Sentinel 控制台，没有东西，调一次order-sentinel的接口后，再刷新就有了

流控规则-QPS 、流控模式-直接、流控效果-快速失败
sentinel 控制台设置/order/flow QPS 为 1（每秒一个请求）
访问 http://localhost:8861/order/flow

流控规则-并发线程数 、流控模式-直接、流控效果-快速失败
sentinel 控制台设置/order/flowThread 并发线程数为 为 1
意思是当 1 个线程还没完成，正在处理中，这时候其他的线程就被限流
只有当这个请求完成了，才能处理其他请求
开两个浏览器 Chrome 和 safari
访问 http://localhost:8861/order/flowThread

BlockExceptionHandler统一异常处理

流控模式-直接、关联、链路

直接：对当前资源生效（上面的就是）

流控规则-QPS 、流控模式-关联、流控效果-快速失败
访问/order/add超过阈值，触发/order/get限流
控制台中操作：对/order/get设置流控规则，QPS阈值为2，流控模式-关联,关联资源为/order/add
意思就是当/order/add每秒请求数大于2的时候，就不再请求/order/get了(被限流了）
要使用jmeter: 设置 100 秒内 300 个线程，也就是一秒 3 个，请求/order/add
再去浏览器中请求 http://localhost:8861/order/get ，可以看到接口被限流了，停掉 jmeter请求后，就能正常访问了


流控规则-QPS 、流控模式-链路、流控效果-快速失败
/order/test1和/order/test2都调用了orderService.getUser()
orderService.getUser()被@SentinelResource注解定义成了sentinel资源，簇点链路页面可以看到，可以对其添加流控规则
需求：对orderService.getUser()进行流控，但是只希望/order/test2受影响
sentinel默认将调用链路收敛（控制台簇点链路中只会出现一个getUser）导致链路流控模式无效，需要在配置文件中加入web-context-unify: false
控制台操作：对getUser设置流控规则，QPS 为 2 ，入口资源为/order/test2
访问：http://localhost:8861/order/test1 不会被限流
访问：http://localhost:8861/order/test2 超过阈值就会被流控


3 种流控效果——快速失败、Warm Up、排队等待

流控规则-QPS 、流控模式-直接 、流控效果-Warm Up
系统长期处于低水位，如果瞬间进来大量的流量，容易把系统打垮
可以通过"冷启动"，让通过的流量缓慢增加，在一定时间内逐渐 增加到阈值上限，给冷系统一个预热的时间，避免冷系统被压垮
冷加载因子: codeFactor 默认是3，即请求 QPS 从 threshold / 3 开始，经预热时长逐渐升至设定的 QPS 阈值
控制台操作 /order/warmup QPS为 10，流控效果为 WarmUp，预热时长 5 （单位 s)
(请求数量会从 3 慢慢的，在 5 秒内递增到 10）
jmeter: 10秒进 300 个线程，调/order/warmup
控制台看：实时监控，通过的 QPS会从 3 慢慢的增加到 10

流控规则-QPS 、流控模式-直接 、流控效果-排队等待

熔断策略
慢调用比例 (SLOW_REQUEST_RATIO)：

openfeign 整合 sentinel
order-openfeign-sentinel (调用方)
stock-nacos(提供方)
之前的 order-openfeign使用了FeignClient调用其他服务，那如果服务提供者出现异常，就会把异常带过来，
调用方怎么处理呢？就是 openfeign 整合 sentinel，然后使用降级处理
1.添加openfeign依赖
2.配置文件 feign.sentinel.enabled: true
3.openfeign 接口 StockFeignService
4.openfeign 的 fallback 实现类 StockFeignServiceFallback（必须实现StockFeignService）

热点规则
order-sentinel服务
必须使用@SentinelResource
热点参数限流会统计传入参数中的热点参数，并根据配置的限流阈值与模式，
对包含热点参数的资源调用进行限流。热点参数限流可以看做是一种特殊的流量控制，仅对包含热点参数的资源调用生效。

系统保护规则 LOAD、RT、线程数、入口 QPS、CPU 使用率
sentinel控制台：系统规则，新增系统规则，cpu使用率，阈值 0.1
随便访问一个接口：http://localhost:8861/order/get/6 有时就会返回"触发系统保护规则了"

sentinel 规则持久化


# Seata
> 要搭建 mysql 等，未实际测试

场景：下单，order服务中，调 stock 服务扣减库存，然后 order 服务中新增订单
未使用 seata:
order-seata服务
stock-seata服务

使用 seata:
alibaba-order-seata服务
alibaba-stock-seata服务


# Spring cloud gateway


# SkyWalking















