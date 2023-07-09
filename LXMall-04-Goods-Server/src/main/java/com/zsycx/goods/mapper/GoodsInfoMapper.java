package com.zsycx.goods.mapper;

import com.zsycx.goods.domain.GoodsInfo;

import java.math.BigDecimal;

public interface GoodsInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);

    GoodsInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsInfo record);

    int updateByPrimaryKey(GoodsInfo record);

    Integer inventoryReduction(Long goodsId, Integer buyNum);

    BigDecimal selectGoodsPrice(Long goodsId);

    void goodsStockRollback(Long goodsId, Integer buyNum);
}