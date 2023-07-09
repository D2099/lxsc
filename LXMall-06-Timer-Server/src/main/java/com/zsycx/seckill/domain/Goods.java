package com.zsycx.seckill.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: Goods
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
public class Goods {

    private Long id;
    private Integer store;
    private BigDecimal buyPrice;
    private Date startTime;
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", store=" + store +
                ", price=" + buyPrice +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
