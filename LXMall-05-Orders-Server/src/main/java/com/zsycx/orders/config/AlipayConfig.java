package com.zsycx.orders.config;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @ClassName: AlipayConfig
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
/**
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000122683533";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC3qFAcCpkrcCGpiMnjZxzDa54u6/A82v2WaiOOcPi34s0M9S9Sjnnwj+eFJJRdHu2yB2u8wDXbc+sPDYMfs7/zLhkubnMAuHLOTwsSVSzHysyrXWZdeeVvf5dkTfFqZNSg0wfeL5X4kw8IPrZPazv7aX6s2yFHw13HdcOxXmWxkt8fhjVmer8Y/7MH1s7oVHPz/iOK2xZfmlRPEqpVLlLzWo54q1zw2fmjsYLBffmtvu11l46KURyG6Ww0c5+1ktxdIV+8dw2pWXtr1lJQJQCVG9hQrWEduJXk2QlGcL0r3lbVqOK9U6CKPMaWsynYq4jr4+2z5ca0wPnadsvjCKQlAgMBAAECggEBAID5qoiGyNpAfONaNK2GFCUIxqWc+7FA+SVQlHfLzl8U33tiFEGd3PSbmrNd5PMgbTzBCWAce7YJyeFgU91Hll1o2jZJSmoPHlDUIapWNoR4XxNZHDI5EG8k42SFeCyHcuseSEA4mtFeNQhNQiqz3APLKjKqEM0c3hbdFPjSmggBM8Jh3mw+cRg+ZgPXnmBtzblTy/1jTC+cfGxtwGSoTml9QnohsQOJtgBwqYAjSOq7EyZdBqnO5rnhBCgipybaop89f4vi/sJA1eHK+9RCvAtI6l6GfTVEa4Q7ivwJ5Zrk2uTCkFA+vYpIUzRJ06isAFyJuQS3KQZojRt9hlZ12wECgYEA5NKblkCqVMrs/VZAesyThiopMlq7BL3XCiM+Lkdq8kpTaC97+Bo72bxDQZxvUkc7nYs3o0bDRODRxm/bjkcKwQd7ErUq/J+0vDRUkCreLl9QO4kU6otWQe0KgqYGgKJMCMfNTOaq5h8Z7vf/6fExLo5Vanoq2d8UgA5a2bUqkB0CgYEAzXhzAVJrSPA6gEhzS74PnM5RVJdSQSPfb1KIGSomPHkVJP+x4/xtH4k8GAYPRWGYjLyseRZQB+zkFaziB1J81ycDtcUz4563QfBZUowqS78ifJDbi9I6/KiD/sZjuwUhVeB7wHPVSJXpOwxn0y4wH6s9UP7QKdsMnhXq5gJHtakCgYAwdWWbuVAZKuxrbjOzjdWEKTOWRV6rz2RV8ka92EQO0ay+OKghT7TzFIqNv4pXu1wlrhD3VAoQcSgbxR6TD/p4DJSEzrcuzsegv9hUu69X1LmSEiLK1P1Mct2x82UXIsfFaELTEVqJ53Y/hGe+7GRE/sGaDgs7HqIhyGh3CIXeBQKBgQCLq1XZzzG4efv6WcVt9jtcpu6oJti2CPGIFb0IBhvJvbD+3oUI6f0vk7Tyy6Oz0PzGpjZDdURfrsr4ZPWcbpkIE7A07mEwWCJQQ1Ykg7z4kmuZ60oiCH0qZE6dcDQ42+rmGltk3hhcZ/MLvYoSBnKWBGwA08WivhEcoN91DJW8SQKBgEftWU4wtMqCsaVGh0OuHyCsIoUL52pIPqIPKP7mMm9FEEluO0od2ksSDkUv51Jmk/aC3GpI3thTJEYB4oULh6tXu/wTPpXNW7y3wOt1LenOAweHJdadKV1GasAlv0tdzgdQRakD9X8CiK0Y0RrfVYekZOyaZwyGcCLlyr4FHdUZ";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhpTrU0RpzF0qcm0HGrcSVsd1uaKRy3THhKD4l6MwS+6101PH8UOW2lkNxk6FqTufK/D0zaALnD4QjYoVBXzJHvgSXKe97Hp5rWlblLhsM0/sxuPt6rCUGH97v+drpy4T1hX5XVOkq5rign2kN5/EMuW01PczzOeCg7UzVKrNDuw6ei31hvNYODy2M7DfooF8Bpfd43rkZ+GMALQmHaiDB5YIgHsq7MJ5z+2D2DoW+jxksdXYNhpDF2YNibIh9RSaC3zsgf6u79C4Y5HiIMVRg/PsLOMbTvkAXjoFSlAns0IsUCFTDZI1oVb1B19SHbcitEuFHo1TJkj2dwldN5lARwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8083/notify_url";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8083/paySuccess";

    public static String format = "json";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关（真实支付API地址）
    // public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
    // 支付宝沙箱网关模拟支付API地址
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "E:\\lxscTool\\alipay_log";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
