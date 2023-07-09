package com.zsycx.seckill;

public final class Constants {
    private Constants() {}
    //商品库存在Redis中的统一Key前缀
    public static final String SEC_KILL_GOODS_STORE="SEC_KILL_GOODS_STORE:";
    //用户购买记录在Redis中的统一Key前缀
    public static final String SEC_KILL_PURCHASE_LIMITS="SEC_KILL_PURCHASE_LIMITS:";
    //订单备份数据在Redis中的Key
    public static final String SEC_KILL_ORDERS="SEC_KILL_ORDERS";
    //订单结果在Redis中的统一key前缀
    public static final String SEC_KILL_ORDERS_RESULT="SEC_KILL_ORDERS_RESULT:";
    //分布式锁在Redis中的key前缀
    public static final String SEC_KILL_LOCK="SEC_KILL_LOCK:";
}