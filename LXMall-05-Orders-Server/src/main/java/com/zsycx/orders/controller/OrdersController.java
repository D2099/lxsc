package com.zsycx.orders.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.zsycx.commons.Code;
import com.zsycx.commons.JsonResult;
import com.zsycx.orders.config.AlipayConfig;
import com.zsycx.orders.domain.Orders;
import com.zsycx.orders.service.OrdersService;
import com.zsycx.orders.service.remote.RemoteGoodsInfoService;
import com.zsycx.orders.service.remote.RemoteUserService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: OrderController
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@RestController
@CrossOrigin
public class OrdersController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RemoteUserService remoteUserService;

    @Resource
    private RemoteGoodsInfoService remoteGoodsInfoService;

    @Resource
    private OrdersService ordersService;

    @RequestMapping("/addOrder")
    public Object addOrder(Long goodsId, String token, Integer buyNum) {

        // System.out.println("商品ID:" + goodsId);
        // System.out.println("token:" + token);
        // System.out.println("购买数量:" + buyNum);

        JsonResult<Long> jsonResult = remoteUserService.getUserId(token);

        if (jsonResult.getCode().equals(Code.NO_LOGIN.getCode())) {
            return jsonResult;
        }

        Orders result = ordersService.addOrder(goodsId, buyNum, jsonResult.getResult());

        if (result == null) {
            return new JsonResult<>(Code.NO_GOODS_STORE, null);
        }

        HashMap<String, Object> ordersInfo = new HashMap<>();

        ordersInfo.put("ordersId", result.getId());
        ordersInfo.put("ordersMoney", result.getOrdersMoney());
        ordersInfo.put("orderNo", result.getOrderNo());

        return new JsonResult<>(Code.OK, ordersInfo);
    }

    @RequestMapping("/getConfirmOrdersInfoList")
    public Object getConfirmOrdersInfo(Long ordersId) {

        List<Map> confirmOrdersInfo = ordersService.getConfirmOrdersInfo(ordersId);

        return new JsonResult<>(Code.OK, confirmOrdersInfo);
    }

    @RequestMapping("/pay")
    public Object pay(String token, Long ordersId, String orderNo, BigDecimal actualMoney, Integer point, Long addressId, String payType) {

        System.err.println("com.zsycx.orders.controller.OrdersController.pay:" + orderNo);

        JsonResult<Long> userIdJsonResult = remoteUserService.getUserId(token);

        if (userIdJsonResult.getCode().equals(Code.NO_LOGIN.getCode())) {
            return "用户未登录";
        }
        // 用户 ID
        Long userId = userIdJsonResult.getResult();

        // 将需要的信息存入 Map 集合中
        HashMap<String, Object> payInfoMap = new HashMap<>();
        payInfoMap.put("id", ordersId);
        payInfoMap.put("addressId", addressId);
        payInfoMap.put("point", point);
        payInfoMap.put("orderNo", orderNo);

        // 将 Map 集合转换为 json 字符串
        String payInfoJson = JSONObject.toJSONString(payInfoMap);
        // 设置超时时间
        Duration timeout = Duration.ofSeconds(60 * 30);

        stringRedisTemplate.execute(new SessionCallback<Object>() {
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                // 开启事务
                redisOperations.multi();

                // 存入 Redis 中
                redisOperations.opsForValue().setIfAbsent((K) ("orderNo:" + orderNo), (V) payInfoJson, timeout);

                redisOperations.opsForZSet().add((K) "orders", (V) payInfoJson, System.currentTimeMillis());

                return redisOperations.exec();
            }
        });

        // 判断支付方式是否为支付宝支付
        if ("zfb".equals(payType)) {
            try {
                return alipay(ordersId, orderNo, actualMoney, addressId, userId);
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        } else {
            return "目前只支持支付宝";
        }

        return "准备支付";
    }

    @RequestMapping("/checkOrdersPay")
    public Object checkOrdersPay(String ordersJson) throws AlipayApiException {

        // System.out.println("OrdersController.java:142:ordersJson:" + ordersJson);

        // 创建并配置支付客户端配置
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.app_id,AlipayConfig.merchant_private_key,AlipayConfig.format,AlipayConfig.charset,AlipayConfig.alipay_public_key,AlipayConfig.sign_type);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();

        // 将接收到的订单 json 字符串转换为对象，方便使用
        Orders orders = JSONObject.parseObject(ordersJson, Orders.class);
        // System.out.println("OrdersController.java:150:orders:" + orders);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orders.getOrderNo());

        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            // System.out.println("调用成功");
            // System.out.println("OrdersController.java:160:response.getBody():" + response.getBody());
            // 获取需要的参数
            Object alipay_trade_query_response = JSONObject.parseObject(response.getBody()).get("alipay_trade_query_response");
            Object trade_status = JSONObject.parseObject(alipay_trade_query_response.toString()).get("trade_status");
            // System.out.println("OrdersController.java:163:trade_status:" + trade_status);
            ordersService.checkOrdersPay((String) trade_status, orders);
        } else {
            System.out.println("调用失败");
        }

        return new JsonResult<>(Code.OK, null);
    }

    @RequestMapping("/paySuccess")
    public String paySuccess(String out_trade_no, BigDecimal total_amount) {

        // System.out.println("orderNo:" + out_trade_no);
        // System.out.println("actualMoney:" + total_amount);

        // 获取需要的订单信息
        String payInfoJson = stringRedisTemplate.opsForValue().get("orderNo:" + out_trade_no);
        // System.err.println("OrdersController-paySuccess-payInfoJson:" + payInfoJson);
        Orders orders = JSONObject.parseObject(payInfoJson, Orders.class);
        // System.err.println("OrdersController-paySuccess-orders:" + orders);

        if (orders == null) {
            return "<h1 style='color: red'>异常的请求结果</h1>";
        }

        orders.setActualMoney(total_amount);
        orders.setStatus(1);
        // 更新订单状态，执行成功后返回受响应行数
        Integer result = ordersService.setOrderInfoStatus(orders);

        // if (result > 0) {
        //     // 删除 Redis 中的订单信息（可选操作）
        // }

        return "支付成功";
    }

    /**
     * 支付宝支付方法
     * @param ordersId
     * @param orderNo
     * @param actualMoney
     * @param addressId
     * @param userId
     * @return
     * @throws AlipayApiException
     */
    private String alipay(Long ordersId, String orderNo, BigDecimal actualMoney, Long addressId, Long userId) throws AlipayApiException {
        // 创建并配置支付客户端配置
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.app_id,AlipayConfig.merchant_private_key,AlipayConfig.format,AlipayConfig.charset,AlipayConfig.alipay_public_key,AlipayConfig.sign_type);

        // 创建支付请求对象
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl(AlipayConfig.notify_url);
        //同步跳转地址，仅支持http/https
        request.setReturnUrl(AlipayConfig.return_url);

        /******必传参数******/
        JSONObject bizContent = new JSONObject();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", orderNo);
        //支付金额，最小值0.01元
        bizContent.put("total_amount", actualMoney);
        //订单标题，不可使用特殊符号
        bizContent.put("subject", "老邪商城商品测试");
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        // 将Json配置设置到支付请求对象中
        request.setBizContent(bizContent.toString());

        // 创建支付响应对象
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            // 获取支付响应体
            String responseBody = response.getBody();
            // 打印响应体
            System.out.println("OrdersController-alipay-responseBody:" + responseBody);
            // 支付成功返回响应体
            return responseBody;
        } else {
            System.out.println("调用失败");
            return "支付失败";
        }

    }
    @RequestMapping("/test")
    public void test(Orders orders) {
        ordersService.setOrderInfoStatus(orders);
    }
}
