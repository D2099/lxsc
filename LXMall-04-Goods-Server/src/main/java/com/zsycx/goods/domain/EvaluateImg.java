package com.zsycx.goods.domain;

public class EvaluateImg {
    private Long id;

    private String imageUrl;

    private Long evaluateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(Long evaluateId) {
        this.evaluateId = evaluateId;
    }

    @Override
    public String toString() {
        return "EvaluateImg{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", evaluateId=" + evaluateId +
                '}';
    }
}