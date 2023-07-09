package com.zsycx.goods.service.impl;

import com.zsycx.goods.mapper.GoodsInfoMapper;
import com.zsycx.goods.service.GoodsInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @ClassName: GoodsInfoServiceImpl
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public BigDecimal inventoryReductionAndGetPrice(Long goodsId, Integer buyNum) {

        Integer result = goodsInfoMapper.inventoryReduction(goodsId, buyNum);
        System.err.println("GoodsInfoServiceImpl-result:" + result);
        if (result < 1) {
            return null;
        }

        return goodsInfoMapper.selectGoodsPrice(goodsId);
    }

    @Override
    public void goodsTimeoutStockRollback(Long goodsId, Integer buyNum) {
        goodsInfoMapper.goodsStockRollback(goodsId, buyNum);
    }
}
