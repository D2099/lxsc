package com.zsycx.goods.controller;

import com.zsycx.commons.Code;
import com.zsycx.commons.JsonResult;
import com.zsycx.commons.PageBean;
import com.zsycx.goods.domain.Evaluate;
import com.zsycx.goods.service.EvaluateService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: EvaluateController
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@RestController
@CrossOrigin // 允许跨域 Ajax
public class EvaluateController {
    @Resource
    private EvaluateService evaluateService;

    @RequestMapping(value = "/getEvaluateList", method = RequestMethod.GET)
    public Object getEvaluateList(Long goodsId, String evaluateLevel, Long pageNo, Long pageSize) {

        System.out.println("goodsId:" + goodsId);
        System.out.println("evaluateLevel:" + evaluateLevel);
        System.out.println("pageNo:" + pageNo);
        System.out.println("pageSize:" + pageSize);

        PageBean<List<Evaluate>> pageBean = evaluateService.getPageBean(goodsId, evaluateLevel, pageNo, pageSize);

        return new JsonResult<>(Code.OK, pageBean);
    }

    @RequestMapping(value = "/countEvaluateNum", method = RequestMethod.GET)
    public Object countEvaluateNum(String goodsId) {

        Map<String, Long> countMap = evaluateService.countEvaluateNum(goodsId);

        return new JsonResult<>(Code.OK, countMap);
    }
}
