package com.zsycx.goods.service;

import java.math.BigDecimal;

/**
 * @ClassName: GoodsInfoService
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
public interface GoodsInfoService {

    BigDecimal inventoryReductionAndGetPrice(Long goodsId, Integer buyNum);

    void goodsTimeoutStockRollback(Long goodsId, Integer buyNum);
}
