package com.zsycx.orders.service.remote;

import com.zsycx.commons.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@FeignClient(name = "LXMall-03-User-Server")
public interface RemoteUserService {

    @RequestMapping("/getUserId")
    JsonResult<Long> getUserId(@RequestParam String token);
}
