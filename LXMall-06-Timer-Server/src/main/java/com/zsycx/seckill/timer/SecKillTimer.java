package com.zsycx.seckill.timer;

import com.zsycx.seckill.Constants;
import com.zsycx.seckill.domain.Goods;
import com.zsycx.seckill.service.remote.RemoteSecKillGoodsService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: SecKillTimer
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@Component
public class SecKillTimer {

    @Resource
    private RemoteSecKillGoodsService remoteSecKillGoodsService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Scheduled(cron = "* * * * * *")
    public void initSecKillGoods() {
        List<Goods> goodsList = remoteSecKillGoodsService.getGoodsList();
        goodsList.forEach(goods -> {
            // 使用StringRedisTemplate向Redis中存入数据时需要将某些类型转换为字符串在进行存入
            stringRedisTemplate.opsForHash().putIfAbsent(Constants.SEC_KILL_GOODS_STORE + goods.getId(), "store", goods.getStore() + "");
            stringRedisTemplate.opsForHash().putIfAbsent(Constants.SEC_KILL_GOODS_STORE + goods.getId(), "buyPrice", goods.getBuyPrice() + "");
            stringRedisTemplate.opsForHash().putIfAbsent(Constants.SEC_KILL_GOODS_STORE + goods.getId(), "startTime", goods.getStartTime().getTime() + "");
            stringRedisTemplate.opsForHash().putIfAbsent(Constants.SEC_KILL_GOODS_STORE + goods.getId(), "endTime", goods.getEndTime().getTime() + "");
        });
    }

    @Scheduled(cron = "0/5 * * * * *")
    public void replenishmentOrder() {
        long scopeOfSupplement = System.currentTimeMillis() - 1000 * 60 * 5;
        Set<String> ordersSet = stringRedisTemplate.opsForZSet().rangeByScore(Constants.SEC_KILL_ORDERS, 0, scopeOfSupplement);
        ordersSet.forEach(order -> amqpTemplate.convertAndSend("secKillExchange", "", order));
    }
}
