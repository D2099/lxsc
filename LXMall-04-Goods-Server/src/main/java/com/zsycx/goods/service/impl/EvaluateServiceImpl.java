package com.zsycx.goods.service.impl;

import com.zsycx.commons.PageBean;
import com.zsycx.goods.domain.Evaluate;
import com.zsycx.goods.mapper.EvaluateMapper;
import com.zsycx.goods.service.EvaluateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @ClassName: EvaluateServiceImpl
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@Service
public class EvaluateServiceImpl implements EvaluateService {

    @Resource
    private EvaluateMapper evaluateMapper;

    @Override
    public PageBean<List<Evaluate>> getPageBean(Long goodsId, String evaluateLevel, Long pageNo, Long pageSize) {

        PageBean<List<Evaluate>> pageBean = new PageBean<>(pageNo, pageSize);

        List<Evaluate> evaluateList = null;

        // 通过判断集合中没有图片的用户评论并且删除
        // if ("image".equals(evaluateLevel)) {
        //     evaluateList = evaluateMapper.selectEvaluateListEnhanced(goodsId, evaluateLevel, pageBean.getSkipNum(), pageBean.getPageSize());
        //
        //     if (evaluateList != null) {
        //         Iterator<Evaluate> iterator = evaluateList.iterator();
        //         while (iterator.hasNext()) {
        //             if (iterator.next().getEvaluateOther().getEvaluateImgList().size() == 0) {
        //                 iterator.remove();
        //             }
        //         }
        //         pageBean.setTotalNum(Long.parseLong(evaluateList.size() + ""));
        //         pageBean.setData(evaluateList);
        //     }
        // 通过数据库查询有图评价信息
        if ("image".equals(evaluateLevel)) {
            Long totalNum = evaluateMapper.selectEvaluateByImageGoodsId(goodsId);

            System.out.println("totalNum:" + totalNum);

            pageBean.setTotalNum(totalNum);
            evaluateList = evaluateMapper.selectEvaluateImageListEnhanced(goodsId, pageBean.getSkipNum(), pageBean.getPageSize());
            System.err.println("pageBean.getSkipNum():" + pageBean.getSkipNum());
            System.err.println("pageBean.getPageSize():" + pageBean.getPageSize());
            System.err.println("获取有图评价：" + evaluateList.size());
            pageBean.setData(evaluateList);
        } else { // 从数据库中查询用户评论
            Long totalNum = evaluateMapper.selectEvaluateByGoodsId(goodsId, evaluateLevel);

            System.out.println("totalNum:" + totalNum);

            pageBean.setTotalNum(totalNum);

            evaluateList = evaluateMapper.selectEvaluateListEnhanced(goodsId, evaluateLevel, pageBean.getSkipNum(), pageBean.getPageSize());

            pageBean.setData(evaluateList);
        }

        System.out.println("pageBean:" + pageBean);

        return pageBean;
    }

    @Override
    public Map<String, Long> countEvaluateNum(String goodsId) {

        Map<String, Long> countMap = evaluateMapper.countEvaluateNumMap(goodsId);

        return countMap;
    }
}
