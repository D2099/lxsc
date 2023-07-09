package com.zsycx.orders.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsycx.commons.Code;
import com.zsycx.commons.JsonResult;
import com.zsycx.orders.domain.OrderInfo;
import com.zsycx.orders.domain.Orders;
import com.zsycx.orders.mapper.OrderInfoMapper;
import com.zsycx.orders.mapper.OrdersMapper;
import com.zsycx.orders.service.OrdersService;
import com.zsycx.orders.service.remote.RemoteGoodsInfoService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName: OrdersServiceImpl
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Resource
    private DataSource dataSource;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private RemoteGoodsInfoService remoteGoodsInfoService;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    @Transactional
    @GlobalTransactional
    public Orders addOrder(Long goodsId, Integer buyNum, Long userId) {

        // System.out.println("OrdersServiceImpl-datasource:" + dataSource.getClass());
        // String xid = RootContext.getXID();
        // System.err.println("OrdersServiceImpl-xid_order:" +xid );

        JsonResult<BigDecimal> price = remoteGoodsInfoService.inventoryReduction(goodsId, buyNum);

        // 如果进入判断语句块表示没有库存
        if (price.getCode().equals(Code.NO_GOODS_STORE.getCode())) {
            return null;
        }

        Orders orders = new Orders();

        orders.setOrdersMoney(price.getResult().multiply(new BigDecimal(buyNum)));
        orders.setPoint(0);
        orders.setStatus(0);
        orders.setCreateTime(System.currentTimeMillis());
        orders.setUserId(userId);
        orders.setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""));

        ordersMapper.insertSelective(orders);

        // System.out.println(1 / 0); // 制造异常测试分布式事务

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setPrice(orders.getOrdersMoney());
        orderInfo.setAmount(buyNum);
        orderInfo.setGoodsId(goodsId);
        orderInfo.setOrdersId(orders.getId());

        orderInfoMapper.insert(orderInfo);
        // --------------------- 将用户订单发送到死信队列进行处理 ---------------------
        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("orders", orders);
        ordersMap.put("orderInfo", orderInfo);
        String ordersMapJson = JSONObject.toJSONString(ordersMap);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("30000");

        Message message = new Message(ordersMapJson.getBytes(), messageProperties);
        amqpTemplate.send("ordersHandlerExchange", "", message);
        // --------------------- 将用户订单发送到死信队列进行处理 ---------------------
        return orders;
    }

    @Override
    public List<Map> getConfirmOrdersInfo(Long ordersId) {

        return orderInfoMapper.selectConfirmOrderInfoByOrdersId(ordersId);
    }

    @Override
    public Integer setOrderInfoStatus(Orders orders) {

        int row = ordersMapper.updateByPrimaryKeySelective(orders);

        amqpTemplate.convertAndSend("ordersPayExchange", "", JSONObject.toJSONString(orders));

        return row;
    }

    @Override
    public void checkOrdersPay(String trade_status, Orders orders) {

        if ("TRADE_SUCCESS".equals(trade_status)) {
            int row = ordersMapper.updateByPrimaryKeySelective(orders);

            amqpTemplate.convertAndSend("ordersPayExchange", "", JSONObject.toJSONString(orders));
        }
    }

    @Transactional
    @GlobalTransactional
    public void ordersTimeoutRollbackHandler(Orders orders, OrderInfo orderInfo) {
        // 通过 id 获取查询订单信息
        Orders ordersQuery = ordersMapper.selectByPrimaryKey(orders.getId());
        // 判断订单状态是否为待支付状态，进入循环表示支付超时回退订单
        if (ordersQuery.getStatus() == 0) {
            orderInfoMapper.deleteByOrdersId(orderInfo.getOrdersId());
            ordersMapper.deleteByPrimaryKey(orders.getId());
            remoteGoodsInfoService.goodsTimeoutStockRollback(orderInfo.getGoodsId(), orderInfo.getAmount());
            // 制造异常测试 seata 全局事务
            // System.out.println(1 / 0);
        }
    }
}
