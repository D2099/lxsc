package com.zsycx.seckill.controller;

import com.zsycx.commons.Code;
import com.zsycx.commons.JsonResult;
import com.zsycx.seckill.domain.Goods;
import com.zsycx.seckill.service.GoodsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: GoodsController
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@RestController
@CrossOrigin // 允许跨域
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @RequestMapping("/getGoodsList")
    public Object getGoodsList() {

        List<Goods> goodsList = goodsService.getGoodsList();

        return goodsList;
    }

    @RequestMapping("/secKill")
    public Object secKill(Long goodsId) {
        // 模拟用户ID
        Long uid = 8L;
        Integer result = goodsService.secKill(uid, goodsId);
        System.out.println("GoodsController.java:38:result:" + result);

        switch (result) {
            case 1:
                return new JsonResult<>(Code.ERROR, "活动还没有开始呢~", result);
            case 2:
                return new JsonResult<>(Code.ERROR, "活动已经结束了呢~", result);
            case 3:
                return new JsonResult<>(Code.ERROR, "商品已卖完下次再来吧~", result);
            case 4:
                return new JsonResult<>(Code.ERROR, "已经购买该商品了呢~", result);
        }

        return new JsonResult<>(Code.OK, result);
    }
}
