package com.zsycx.seckill.listener;

import com.alibaba.fastjson.JSONObject;
import com.zsycx.seckill.Constants;
import com.zsycx.seckill.domain.Orders;
import com.zsycx.seckill.service.OrdersService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName: OrdersListener
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@Component
public class OrdersListener {

    @Resource
    private OrdersService ordersService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(
            bindings = {@QueueBinding(
                    value = @Queue("secKillQueue"),
                    exchange = @Exchange(
                            name = "secKillExchange",
                            type = "fanout"
                    )
            )}
    )
    public void secKillOrdersListener(String message) {
        // System.out.println(message);
        Orders orders = JSONObject.parseObject(message, Orders.class);
        // System.out.println(orders);
        // System.out.println(orders.getClass());
        Integer result = ordersService.addOrders(orders);
        // 如果结果等于 0 表示添加成功，删除 Redis 中的补单数据
        if (result == 0) {
            stringRedisTemplate.opsForZSet().remove(Constants.SEC_KILL_ORDERS, message);
        }
    }
}
