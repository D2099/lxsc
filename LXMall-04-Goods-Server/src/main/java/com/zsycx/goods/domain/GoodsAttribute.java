package com.zsycx.goods.domain;

public class GoodsAttribute {
    private Long id;

    private Long arrtId;

    private Long goodsId;

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArrtId() {
        return arrtId;
    }

    public void setArrtId(Long arrtId) {
        this.arrtId = arrtId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}