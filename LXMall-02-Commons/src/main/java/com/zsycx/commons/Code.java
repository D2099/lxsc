package com.zsycx.commons;

/**
 * @ClassName: Code
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */

/**
 * 请求响应状态码
 */
public enum Code {
    OK("10000", "请求成功"),
    ERROR("10001", "请求失败"),
    NO_LOGIN("11000", "用户未登录"),
    NO_CONFIRM_ORDERS("11001", "没有确认订单"),
    NO_GOODS_STORE("11002", "没有库存");

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    Code(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
