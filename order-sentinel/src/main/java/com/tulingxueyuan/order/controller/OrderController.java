package com.tulingxueyuan.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tulingxueyuan.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/***
 * @author guchaolong
 * 
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    /**
     * 流控规则-QPS
     * 流控模式-直接
     * 流控效果-快速失败
     *
     * sentinel 控制台设置/order/flow QPS 为 1（每秒一个请求）
     * 访问 http://localhost:8861/order/flow
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/flow")
    //因为使用了MyBlockExceptionHandler，下面的@SentinelResource就可以注释掉了
    //@SentinelResource(value = "flow",blockHandler = "flowBlockHandler")
    public String flow() throws InterruptedException {
        return "正常访问/order/flow";
    }


    public String flowBlockHandler(BlockException e) {
        return "流控";
    }

    /**
     * 流控规则-并发线程数
     * 流控模式-直接
     * 流控效果-快速失败
     *
     * sentinel 控制台设置/order/flowThread 并发线程数为 为 1
     * 意思是当 1 个线程还没完成，正在处理中，这时候其他的线程就被限流
     * 只有当这个请求完成了，才能处理其他请求
     * 开两个浏览器 Chrome 和 safari
     * 访问 http://localhost:8861/order/flowThread
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/flowThread")
    //@SentinelResource(value = "flowThread",blockHandler = "flowBlockHandler")
    public String flowThread() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        System.out.println("正常访问/order/flowThread");
        return "正常访问";
    }


    /**
     * 流控规则-QPS
     * 流控模式-关联
     * 流控效果-快速失败
     *
     * 访问/order/add超过阈值，触发/order/get限流
     * 控制台中操作：对/order/get设置流控规则，QPS阈值为2，流控模式-关联,关联资源为/order/add
     * 意思就是当/order/add每秒请求数大于2的时候，就不再请求/order/get了(被限流了）
     * 要使用jmeter: 设置 100 秒内 300 个线程，也就是一秒 3 个，请求/order/add
     * 再去浏览器中请求 http://localhost:8861/order/get ，可以看到接口被限流了，停掉 jmeter请求后，就能正常访问了
     *
     * @return
     */
    @RequestMapping("/add")
    public String add() {
        System.out.println("下单成功!");
        return "生成订单";
    }

    // 关联流控  访问/add 触发/get
    @RequestMapping("/get")
    public String get() throws InterruptedException {
        return "查询订单";
    }

    @Autowired
    IOrderService orderService;

    /**
     * 流控规则-QPS
     * 流控模式-链路
     * 流控效果-快速失败
     *
     * /order/test1和/order/test2都调用了orderService.getUser()
     * orderService.getUser()被@SentinelResource注解定义成了sentinel资源，簇点链路页面可以看到，可以对其添加流控规则
     * 需求：对orderService.getUser()进行流控，但是只希望/order/test2受影响
     *
     * sentinel默认将调用链路收敛（控制台簇点链路中只会出现一个getUser）导致链路流控模式无效，需要在配置文件中加入web-context-unify: false
     *
     * 控制台操作：对getUser设置流控规则，QPS 为 2 ，入口资源为/order/test2
     * 访问：http://localhost:8861/order/test1 不会被限流
     * 访问：http://localhost:8861/order/test2 超过阈值就会被流控
     *
     *
     * @return
     */
    @RequestMapping("/test1")
    public String test1() {
        return orderService.getUser();
    }

    @RequestMapping("/test2")
    public String test2() throws InterruptedException {
        return orderService.getUser();
    }


    /**
     * 流控规则-QPS
     * 流控模式-直接
     * 流控效果-Warm Up
     *
     * 系统长期处于低水位，如果瞬间进来大量的流量，容易把系统打垮
     * 可以通过"冷启动"，让通过的流量缓慢增加，在一定时间内逐渐 增加到阈值上限，给冷系统一个预热的时间，避免冷系统被压垮
     * 冷加载因子: codeFactor 默认是3，即请求 QPS 从 threshold / 3 开始，经预热时长逐渐升至设定的 QPS 阈值
     *
     * 控制台操作 /order/warmup QPS为 10，流控效果为 WarmUp，预热时长 5 （单位 s)
     * (请求数量会从 3 慢慢的，在 5 秒内递增到 10）
     *
     * jmeter: 10秒进 300 个线程，调/order/warmup
     * 控制台看：实时监控，通过的 QPS会从 3 慢慢的增加到 10
     *
     * @return
     */
    @RequestMapping("/warmup")
    public String test() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "========test()========";
    }

    /**
     * 流控规则-QPS
     * 流控模式-直接
     * 流控效果-快速失败&排队等待
     *
     * 快速失败
     * sentinel 控制台设置/order/rateLimiter QPS 为 5（每秒5个请求）
     * jmeter 模拟脉冲流量：1 秒 10 个请求，循环 4 次，每次间隔 5 秒
     * 控制台观察：实时监控，峰值的时候差不多 5 个通过，5 个被拒绝，峰值后面的 5 秒是空闲期，被浪费掉了
     *
     * 排队等待
     * sentinel 控制台修改/order/rateLimiter QPS 为 5（每秒5个请求），流控效果-排队等待，超时时间 5 秒
     * jmeter 模拟脉冲流量：1 秒 10 个请求，循环 4 次，每次间隔 5 秒
     * 控制台观察：实时监控，几乎没有请求被拒绝了，并且还有一部分空闲期没有被利用
     *
     * jmeter 模拟脉冲流量：1 秒 20 个请求，循环 4 次，每次间隔 5 秒
     * 控制台观察：实时监控，几乎没有请求被拒绝了，空闲期都被利用起来了
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/rateLimiter")
    //因为使用了MyBlockExceptionHandler，下面的@SentinelResource就可以注释掉了
    //@SentinelResource(value = "flow",blockHandler = "flowBlockHandler")
    public String rateLimiter() throws InterruptedException {
        return "正常访问/order/rateLimiter";
    }


    /**
     * 熔断策略-慢调用比例 (SLOW_REQUEST_RATIO)：
     * 选择以慢调用比例作为阈值，需要设置允许的慢调用 RT（即最大的响应时间）， 请求的响应时间大于该值则统计为慢调用。
     * 当单位统计时长（statIntervalMs）内请求数目大于设置的最小请求数目，并 且慢调用的比例大于阈值，则接下来的熔断时长内请求会自动被熔断。
     * 经过熔断时长后熔断器会进入探测恢复状态 （HALF-OPEN 状态），若接下来的一个请求响应时间小于设置的慢调用 RT 则结束熔断，若大于设置的慢调用 RT 则会 再次被熔断。
     *
     * sentinel控制台操作：最大RT 100ms, 阈值：0.5 熔断时长：10s, 最小请求数：10
     * jmeter: 1秒 10 次，请求/order/slowRequestRatio
     * 然后浏览器中请求 http://localhost:8861/order/slowRequestRatio，
     * 可以看到被降级了，过 10s之后，再请求一次，这次能进入（但是还是超时，因为 2>1)，之后的 10s内再请求，又会被降级，再过 10s,......
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/slowRequestRatio")
    //@SentinelResource(value = "flowThread",blockHandler = "flowBlockHandler")
    public String slowRequestRatio() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("正常访问/order/slowRequestRatio");
        return "正常访问";
    }


    /**
     * 熔断策略-异常比例
     * 异常比例 (ERROR_RATIO)：
     * 当单位统计时长（statIntervalMs）内请求数目大于设置的最小请求数目，并且异常的比例 大于阈值，
     * 则接下来的熔断时长内请求会自动被熔断。经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状 态），
     * 若接下来的一个请求成功完成（没有错误）则结束熔断，否则会再次被熔断。异常比率的阈值范围是 [0.0, 1.0]，
     * 代表 0% - 100%

     * sentinel控制台操作：阈值：0.1  熔断时长：10s, 最小请求数：5
     * jmeter: 1秒 10 次，请求/order/err
     * 然后浏览器中请求 http://localhost:8861/order/err，
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/err")
    public String err() {
        int a = 1 / 0;//抛异常
        return "hello";
    }


    /**
     * 热点规则，必须使用@SentinelResource
     * 热点参数限流会统计传入参数中的热点参数，并根据配置的限流阈值与模式，
     * 对包含热点参数的资源调用进行限流。热点参数限流可以看做是一种特殊的流量控制，仅对包含热点参数的资源调用生效。

     * sentinel 控制台：资源getById 热点 ，参数索引 0，单机阈值 10（这个是全局的），统计窗口时长 1，然后点保存，再点击编辑
     * 接着再打开高级选项，参数类型 int,参数值 1，限流阈值 2 （有时会添加不成功，可以多添加几个，然后保存，再编辑，再删除 保存）

     * 接口调用：http://localhost:8861/order/get/1 因为针对1设置了qps为 2，所以 1s内只能处理 2 个，第三个就降级了
     * jmeter: http://localhost:8861/order/get/2 1秒请求 15 次，可以看到前 10 个都是正常访问，第 11 个开始被降级
     *
     * @param id
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/get/{id}")
    @SentinelResource(value = "getById", blockHandler = "HotBlockHandler")
    public String getById(@PathVariable("id") Integer id) throws InterruptedException {
        System.out.println("正常访问");
        return "正常访问";
    }

    public String HotBlockHandler(@PathVariable("id") Integer id, BlockException e) throws InterruptedException {
        return "热点异常处理";
    }

}
