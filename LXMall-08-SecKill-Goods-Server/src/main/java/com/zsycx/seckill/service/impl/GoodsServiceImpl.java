package com.zsycx.seckill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsycx.seckill.Constants;
import com.zsycx.seckill.domain.Goods;
import com.zsycx.seckill.mapper.GoodsMapper;
import com.zsycx.seckill.service.GoodsService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: GoodsServiceImpl
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public List<Goods> getGoodsList() {

        List<Goods> goodsList = goodsMapper.selectSecKillGoodsInfo();

        return goodsList;
    }

    /**
     * 秒杀
     * @param uid
     * @param goodsId
     * @return
     * 返回 0 表示秒杀成功
     * 返回 1 表示秒杀未开始
     * 返回 2 表示秒杀已结束
     * 返回 3 表示没有库存
     * 返回 4 表示重复购买
     * 返回 5 表示购买失败
     * 返回 -88 表示商品数据存在问题
     */
    @Override
    public Integer secKill(Long uid, Long goodsId) {

        String startTime = (String) stringRedisTemplate.opsForHash().get(Constants.SEC_KILL_GOODS_STORE + goodsId, "startTime");
        String endTime = (String) stringRedisTemplate.opsForHash().get(Constants.SEC_KILL_GOODS_STORE + goodsId, "endTime");
        Long systemCurrentTime = System.currentTimeMillis();

        // 判断商品是否在活动时间内
        if (startTime != null && endTime != null) {
            if (systemCurrentTime < Long.parseLong(startTime)) {
                return 1;
            } else if (systemCurrentTime > Long.parseLong(endTime)) {
                return 2;
            }
        }

        // 将信息存入 Map 集合中
        Map ordersMap = new HashMap();
        ordersMap.put("uid", uid);
        ordersMap.put("goodsId", goodsId);
        ordersMap.put("buyPrice", stringRedisTemplate.opsForHash().get(Constants.SEC_KILL_GOODS_STORE + goodsId, "buyPrice"));
        // 转换为 Json 格式
        String ordersJson = JSONObject.toJSONString(ordersMap);

        // 将减少库存操作放入循环中，如果有其他线程抢先执行操作该线程会再次执行
        do {
            // 如果有返回值表示购买失败
            Object execute = stringRedisTemplate.execute(new SessionCallback<Object>() {
                @Override
                public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {

                    List keys = new ArrayList<>();
                    keys.add(Constants.SEC_KILL_GOODS_STORE + goodsId);
                    keys.add(Constants.SEC_KILL_PURCHASE_LIMITS + goodsId + ":" + uid);
                    // 监控 Key 以后相当于锁定了 Key 所对应的数据
                    redisOperations.watch(keys);

                    Object store = stringRedisTemplate.opsForHash().get(Constants.SEC_KILL_GOODS_STORE + goodsId, "store");
                    if (store == null) {
                        return -88;
                    }
                    if (Integer.parseInt(store.toString()) <= 0) {
                        // 解除监控
                        redisOperations.unwatch();
                        return 3;
                    }

                    String purchaseLock = stringRedisTemplate.opsForValue().get(Constants.SEC_KILL_PURCHASE_LIMITS + goodsId + ":" + uid);
                    if (purchaseLock != null) {
                        redisOperations.unwatch();
                        return 4;
                    }

                    // 程序执行到这里表示已经可以尝试购买商品
                    redisOperations.multi();
                    // 尝试减少库存
                    redisOperations.opsForHash().put((K) (Constants.SEC_KILL_GOODS_STORE + goodsId), "store", Integer.parseInt(store.toString()) - 1 + "");
                    // 添加用户购买记录
                    redisOperations.opsForValue().set((K) (Constants.SEC_KILL_PURCHASE_LIMITS + goodsId + ":" + uid), (V) "1");
                    // 将订单信息存入 Redis 中，以防掉单
                    redisOperations.opsForZSet().add((K) Constants.SEC_KILL_ORDERS, (V) ordersJson, System.currentTimeMillis());

                    return redisOperations.exec();
                }
            });

            // System.out.println("GoodsServiceImpl.java:105");
            // System.out.println("GoodsServiceImpl.java:69:execute:" + execute);
            // System.out.println("GoodsServiceImpl.java:69:execute.getClass:" + execute.getClass());

            // 如果购买失败直接返回对应提示码
            if (execute instanceof Integer) {
                return Integer.parseInt(execute.toString());
            }

            List list = (List) execute;
            // 判断结果是否为空，如果不为空进入判断语句表示用户购买操作执行完成，跳出循环
            if (list != null && !list.isEmpty()) {
                break;
            }
        } while (true);

        // 运行到这里表示购买成功
        // 发送到 RabbitMQ 中
        amqpTemplate.convertAndSend("secKillExchange", "", ordersJson);

        // 购买成功
        return 0;
    }
}
