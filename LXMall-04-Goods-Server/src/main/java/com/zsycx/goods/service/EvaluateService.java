package com.zsycx.goods.service;

import com.zsycx.commons.PageBean;
import com.zsycx.goods.domain.Evaluate;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: EvaluateService
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
public interface EvaluateService {
    PageBean<List<Evaluate>> getPageBean(Long goodsId, String evaluateLevel, Long pageNo, Long pageSize);

    Map<String, Long> countEvaluateNum(String goodsId);
}
