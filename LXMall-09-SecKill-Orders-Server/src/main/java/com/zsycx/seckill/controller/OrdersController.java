package com.zsycx.seckill.controller;

import com.alibaba.fastjson.JSONObject;
import com.zsycx.commons.Code;
import com.zsycx.commons.JsonResult;
import com.zsycx.seckill.Constants;
import com.zsycx.seckill.domain.Orders;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @ClassName: OrdersController
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@RestController
@CrossOrigin
public class OrdersController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/getOrdersInfo")
    public Object getOrdersInfo(Long goodsId) {

        System.out.println(goodsId);
        // 模拟用户ID，该数据应该通过 token 从 Redis 中获取
        long uid = 8L;

        String ordersJson = stringRedisTemplate.opsForValue().get(Constants.SEC_KILL_ORDERS_RESULT + goodsId + ":" + uid);
        Orders orders = JSONObject.parseObject(ordersJson, Orders.class);
        if (ordersJson == null) {
            return new JsonResult<>(Code.ERROR, "暂时没有获取到订单", null);
        }

        return new JsonResult<>(Code.OK, orders);
    }
}
