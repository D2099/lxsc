package com.zsycx.user.domain;

public class PointRecord {
    private Long id;

    private Integer point;

    private Integer pointType;

    private String pointDescribe;

    private Long uPointId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getPointType() {
        return pointType;
    }

    public void setPointType(Integer pointType) {
        this.pointType = pointType;
    }

    public String getPointDescribe() {
        return pointDescribe;
    }

    public void setPointDescribe(String pointDescribe) {
        this.pointDescribe = pointDescribe;
    }

    public Long getuPointId() {
        return uPointId;
    }

    public void setuPointId(Long uPointId) {
        this.uPointId = uPointId;
    }
}