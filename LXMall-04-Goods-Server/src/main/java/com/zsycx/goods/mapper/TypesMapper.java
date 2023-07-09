package com.zsycx.goods.mapper;

import com.zsycx.goods.domain.Types;

public interface TypesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Types record);

    int insertSelective(Types record);

    Types selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Types record);

    int updateByPrimaryKey(Types record);
}