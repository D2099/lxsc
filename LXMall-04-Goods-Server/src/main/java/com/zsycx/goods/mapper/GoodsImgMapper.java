package com.zsycx.goods.mapper;

import com.zsycx.goods.domain.GoodsImg;

public interface GoodsImgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsImg record);

    int insertSelective(GoodsImg record);

    GoodsImg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsImg record);

    int updateByPrimaryKey(GoodsImg record);
}