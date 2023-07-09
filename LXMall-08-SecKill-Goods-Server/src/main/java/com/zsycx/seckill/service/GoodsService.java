package com.zsycx.seckill.service;

import com.zsycx.seckill.domain.Goods;

import java.util.List;

/**
 * @ClassName: GoodsService
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
public interface GoodsService {
    List<Goods> getGoodsList();

    Integer secKill(Long uid, Long goodsId);
}
