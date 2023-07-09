package com.zsycx.seckill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsycx.seckill.Constants;
import com.zsycx.seckill.domain.Orders;
import com.zsycx.seckill.mapper.OrdersMapper;
import com.zsycx.seckill.service.OrdersService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;

/**
 * @ClassName: OrdersServiceImpl
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Integer addOrders(Orders orders) {

        try {
            orders.setBuyNum(1);
            orders.setOrderMoney(orders.getBuyPrice().multiply(new BigDecimal(orders.getBuyNum())));
            orders.setCreateTime(new Date());
            orders.setStatus(1);

            int result = ordersMapper.insert(orders);
            // System.out.println(result);
            // System.out.println(orders);
            stringRedisTemplate.opsForValue().set(Constants.SEC_KILL_ORDERS_RESULT + orders.getGoodsId() + ":" + orders.getUid(), JSONObject.toJSONString(orders), Duration.ofSeconds(60 * 5));

        } catch (DuplicateKeyException e) {
            // 向 MySQL 添加数据时抛出的异常，会报该异常因为违反数据库中的唯一约束
            // 使用捕捉异常的方式，解决数据重复问题
            return 0;
        }

        return 0;
    }
}
