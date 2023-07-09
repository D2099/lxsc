package com.zsycx.goods.mapper;

import com.zsycx.goods.domain.Evaluate;

import java.util.List;
import java.util.Map;

public interface EvaluateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Evaluate record);

    int insertSelective(Evaluate record);

    Evaluate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Evaluate record);

    int updateByPrimaryKeyWithBLOBs(Evaluate record);

    int updateByPrimaryKey(Evaluate record);

    Long selectEvaluateByGoodsId(Long goodsId, String evaluateLevel);

    List<Evaluate> selectEvaluateList(Long goodsId, Long skipNum, Long pageSize);

    List<Evaluate> selectEvaluateListEnhanced(Long goodsId, String evaluateLevel, Long skipNum, Long pageSize);

    Long selectEvaluateByImageGoodsId(Long goodsId);

    List<Evaluate> selectEvaluateImageListEnhanced(Long goodsId, Long skipNum, Long pageSize);

    Map<String, Long> countEvaluateNumMap(String goodsId);
}