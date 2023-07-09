package com.zsycx.goods.controller;

import com.zsycx.commons.Code;
import com.zsycx.commons.JsonResult;
import com.zsycx.goods.service.GoodsInfoService;
import io.seata.core.context.RootContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;

/**
 * @ClassName: GoodsInfoController
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@RestController
@CrossOrigin
public class GoodsInfoController {

    @Resource
    private DataSource dataSource;

    @Resource
    private GoodsInfoService goodsInfoService;

    @RequestMapping("/inventoryReduction")
    public Object inventoryReduction(Long goodsId, Integer buyNum) {

        System.out.println("GoodsInfoController-datasource:" + dataSource.getClass());
        String xid = RootContext.getXID();
        System.err.println("GoodsInfoController-xid_order:" +xid );

        BigDecimal price = goodsInfoService.inventoryReductionAndGetPrice(goodsId, buyNum);

        if (price == null) {
            return new JsonResult<>(Code.NO_GOODS_STORE, null);
        }

        return new JsonResult<>(Code.OK, price);
    }

    @RequestMapping("/goodsTimeoutStockRollback")
    public void goodsTimeoutStockRollback(Long goodsId, Integer buyNum) {
        goodsInfoService.goodsTimeoutStockRollback(goodsId, buyNum);
    }
}
