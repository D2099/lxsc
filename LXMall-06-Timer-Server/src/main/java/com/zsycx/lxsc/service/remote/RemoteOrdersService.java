package com.zsycx.lxsc.service.remote;

import com.zsycx.commons.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: RemoteOrdersService
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@FeignClient(name = "LXMall-05-Orders-Server")
public interface RemoteOrdersService {

    @RequestMapping("/checkOrdersPay")
    JsonResult checkOrdersPay(@RequestParam String ordersJson);
}
