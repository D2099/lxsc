package com.zsycx.orders.service;

import com.zsycx.orders.domain.OrderInfo;
import com.zsycx.orders.domain.Orders;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: OrdersService
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
public interface OrdersService {
    Orders addOrder(Long goodsId, Integer buyNum, Long userId);

    List<Map> getConfirmOrdersInfo(Long ordersId);

    Integer setOrderInfoStatus(Orders orders);

    void checkOrdersPay(String trade_status, Orders orders);

    void ordersTimeoutRollbackHandler(Orders orders, OrderInfo orderInfo);
}
