package com.zsycx.orders.listener;

import com.alibaba.fastjson.JSONObject;
import com.zsycx.orders.domain.OrderInfo;
import com.zsycx.orders.domain.Orders;
import com.zsycx.orders.service.OrdersService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @ClassName: OrdersPayListener
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@Component
public class OrdersListener {

    @Resource
    private OrdersService ordersService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("ordersStatusQueue"),
            exchange = @Exchange(
                    name = "ordersStatusExchange",
                    type = "fanout"
            )
    ))
    public void deleteOrdersForRedis(String message) {
        System.out.println(message);
        stringRedisTemplate.opsForZSet().remove("orders", message);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    key = "orderDeadLetterKey",
                    value = @Queue("ordersDeadLetterQueue"),
                    exchange = @Exchange(
                            name = "ordersDeadLetterExchange",
                            type = "direct"
                    )
            )
    )
    public void ordersHandlerListener(String message) {
        System.out.println(message);
        JSONObject messageJsonObject = JSONObject.parseObject(message);

        Orders orders = JSONObject.parseObject(messageJsonObject.get("orders").toString(), Orders.class);
        OrderInfo orderInfo = JSONObject.parseObject(messageJsonObject.get("orderInfo").toString(), OrderInfo.class);

        ordersService.ordersTimeoutRollbackHandler(orders, orderInfo);
    }
}
