package com.zsycx.lxsc.timer;

import com.zsycx.commons.JsonResult;
import com.zsycx.lxsc.service.remote.RemoteOrdersService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @ClassName: OrdersTimer
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@Component // 放入 Spring 容器中
public class OrdersTimer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RemoteOrdersService remoteOrdersService;

    // @Scheduled(cron = "* * * * * *")
    public void checkOrdersPay() {

        long timeMillis = System.currentTimeMillis() - 1000 * 60 * 5;

        Set<String> ordersSet = stringRedisTemplate.opsForZSet().rangeByScore("orders", 0, timeMillis);

        ordersSet.forEach(ordersJson -> {
            JsonResult jsonResult = remoteOrdersService.checkOrdersPay(ordersJson);
            System.out.println("OrdersTimer.java:34:jsonResult:" + jsonResult.getCode());
        });
    }
}
