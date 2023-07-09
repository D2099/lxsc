package com.zsycx.orders.service.remote;

import com.zsycx.commons.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @ClassName: RemoteGoodsInfoService
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@FeignClient(name = "LXMall-04-Goods-Server")
public interface RemoteGoodsInfoService {
    @RequestMapping("/inventoryReduction")
    JsonResult<BigDecimal> inventoryReduction(@RequestParam Long goodsId, @RequestParam Integer buyNum);

    @RequestMapping("/goodsTimeoutStockRollback")
    void goodsTimeoutStockRollback(@RequestParam Long goodsId, @RequestParam Integer buyNum);
}
