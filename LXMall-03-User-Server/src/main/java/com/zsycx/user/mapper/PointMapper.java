package com.zsycx.user.mapper;

import com.zsycx.user.domain.Point;

public interface PointMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Point record);

    int insertSelective(Point record);

    Point selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Point record);

    int updateByPrimaryKey(Point record);
}