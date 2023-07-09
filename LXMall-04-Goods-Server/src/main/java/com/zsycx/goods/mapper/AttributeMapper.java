package com.zsycx.goods.mapper;

import com.zsycx.goods.domain.Attribute;

public interface AttributeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Attribute record);

    int insertSelective(Attribute record);

    Attribute selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Attribute record);

    int updateByPrimaryKey(Attribute record);
}