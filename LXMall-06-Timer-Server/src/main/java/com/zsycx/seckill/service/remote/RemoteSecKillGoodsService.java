package com.zsycx.seckill.service.remote;

import com.zsycx.seckill.domain.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName: RemoteSecKillGoodsService
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@FeignClient(name = "LXMall-08-SecKill-Goods-Server")
public interface RemoteSecKillGoodsService {

    @RequestMapping("/getGoodsList")
    List<Goods> getGoodsList();
}
