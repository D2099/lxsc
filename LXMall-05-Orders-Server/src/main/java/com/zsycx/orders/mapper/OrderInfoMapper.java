package com.zsycx.orders.mapper;

import com.zsycx.orders.domain.OrderInfo;

import java.util.List;
import java.util.Map;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    List<Map> selectConfirmOrderInfoByOrdersId(Long ordersId);

    void deleteByOrdersId(Long ordersId);
}