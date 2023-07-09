package com.zsycx.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zsycx.commons.Code;
import com.zsycx.commons.JsonResult;
import com.zsycx.user.domain.Address;
import com.zsycx.user.domain.User;
import com.zsycx.user.service.AddressService;
import com.zsycx.user.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.*;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@RestController
@CrossOrigin //
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private AddressService addressService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Object login(User user) {

        // 从 Redis 中获取用户登录错误次数
        String userLoginErrNum = stringRedisTemplate.opsForValue().get("userLoginErr:" + user.getPhone());
        // 如果不等于 null 并且 错误信息大于等于 5 表示账户锁定
        if (userLoginErrNum != null && Long.parseLong(userLoginErrNum) >= 5) {
            return new JsonResult<>(Code.ERROR, "账户已锁定，明天再来吧", 555);
        }

        System.out.println("UserController-user01:" + user);

        // 通过 service 层获取登录状态码
        int result = userService.login(user);

        // 判断登录状态码是否为0，不为 0 表示登录失败
        if (result != 0) {
            // 设置登录错误次数
            Long userLoginErr = stringRedisTemplate.opsForValue().increment("userLoginErr:" + user.getPhone());
            // 判断如果不等于 null 并且等于 1，则给用户设置错误次数超时时间
            if (userLoginErr != null && userLoginErr == 1) {
                userLock(user.getPhone());
            }
            return new JsonResult<>(Code.ERROR, "用户名或密码错误，请检查大小写，还有:" + (userLoginErr == null ? null : 6 - userLoginErr) + ":次机会", result);
        }

        // System.out.println("UserController-user02:" + user);

        /**
         * 将用户信息存储到 Redis
         */
        // 生成随机 ID 字符串
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        // 将用户对象转换为 Json 格式字符串
        String userJson = JSON.toJSONString(user);
        // 通过 Duration 类创建超时时间
        Duration timeout = Duration.ofSeconds(60 * 30);
        // 将用户信息存储到 Redis 中
        stringRedisTemplate.opsForValue().set("UserToken:" + token, userJson, timeout);

        // 将登录成功的用户信息保存到集合并返回给前端
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("id", user.getId());
        resultMap.put("phone", user.getPhone());
        resultMap.put("nickName", user.getNickName());

        return new JsonResult<>(Code.OK, "登录成功", resultMap);
    }

    @PostMapping("/registry")
    public Object registry(User user) {
        // 判断手机号码和密码是否为空，如果为空返回 2
        if (user.getPhone() == null || user.getPassword() == null) {
            return new JsonResult<>(Code.ERROR, "注册失败，手机号或密码不能为空", 2);
        } else if (user.getNickName() == null) { // 判断昵称是否为空，为空给用户设置一个空字符串
            user.setNickName("");
        }

        System.out.println("UserController-registry:" + user);

        int regResult = userService.registry(user);
        // 判断注册结果，不等于 0 表示注册不成功
        if (regResult != 0) {
            // 设置状态码为 10002
            Code.ERROR.setCode("10002");
            return new JsonResult<>(Code.ERROR, "注册失败，手机号码已存在，请直接登录", regResult);
        }
        // 设置成功注册用户 token 到 Redis 中
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        Duration timeout = Duration.ofSeconds(60 * 30);
        stringRedisTemplate.opsForValue().set("userToken:" + token, JSON.toJSONString(user), timeout);
        // 将注册成功的用户信息返回给前端
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("id", user.getId());
        resultMap.put("phone", user.getPhone());
        resultMap.put("nickName", user.getNickName());
        return new JsonResult<>(Code.OK, "注册成功", resultMap);
    }

    /**
     * 获取用户 id
     * @param token
     * @return
     */
    @RequestMapping("/getUserId")
    public JsonResult<Long> getUserId(String token) {
        JsonResult<Long> userLoginStateJsonResult = getUserLoginStateJsonResult(token);

        if (userLoginStateJsonResult.getResult() == null) {
            return userLoginStateJsonResult;
        }
        return new JsonResult<>(Code.OK, userLoginStateJsonResult.getResult());
    }

    @RequestMapping("/getConfirmUserAddressList")
    public JsonResult getConfirmUserAddressList(String token) {
        JsonResult<Long> userLoginStateJsonResult = getUserLoginStateJsonResult(token);

        if (userLoginStateJsonResult.getResult() == null) {
            return userLoginStateJsonResult;
        }

        List<Address> userAddressList = addressService.getConfirmUserAddressList(userLoginStateJsonResult.getResult());

        return new JsonResult<>(Code.OK, userAddressList);
    }

    /**
     * 获取用户登陆状态 JsonResult 对象
     * @param token
     * @return
     */
    public JsonResult<Long> getUserLoginStateJsonResult(String token) {
        // 获取 Redis 中的用户 token
        String userJson = stringRedisTemplate.opsForValue().get("UserToken:" + token);

        // 判断是否获取到 token，获取不到返回未登录状态码
        if (userJson == null) {
            return new JsonResult<>(Code.NO_LOGIN, null);
        }

        // 重新设置用户 token 超时时间
        Duration timeout = Duration.ofSeconds(60 * 30);
        stringRedisTemplate.expire("UserToken:" + token, timeout);

        // 将 token json 字符串转换为需要的数据
        long id = JSONObject.parseObject(userJson).getBigInteger("id").longValue();
        // 获取正常返回
        return new JsonResult<>(Code.OK, id);
    }

    /**
     * 设置用户验证错次数超时时间
     * @param phone 需要设置超时时间的用户号码
     */
    private void userLock(String phone) {
        // 通过 Calendar 日历类设置时间为明天 0时 0分 0秒
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, -12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // 时间测试
        // Date date = new Date(calendar.getTimeInMillis());
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // String myDate = sdf.format(date);
        // System.out.println(myDate);

        Duration timeout = Duration.ofSeconds(calendar.getTimeInMillis() / 1000 - System.currentTimeMillis() / 1000);

        // System.out.println(timeout.getSeconds() / 60 + "分钟");
        // System.out.println(timeout.getSeconds() / 60 / 60 + "小时");

        stringRedisTemplate.expire("userLoginErr:" + phone, timeout);
    }
}
