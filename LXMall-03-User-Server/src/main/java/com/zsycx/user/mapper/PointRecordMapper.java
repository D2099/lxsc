package com.zsycx.user.mapper;

import com.zsycx.user.domain.PointRecord;

public interface PointRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PointRecord record);

    int insertSelective(PointRecord record);

    PointRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PointRecord record);

    int updateByPrimaryKey(PointRecord record);
}