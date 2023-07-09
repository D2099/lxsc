package com.zsycx.goods.domain;

import java.util.List;

/**
 * @ClassName: EvaluateOther
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
public class EvaluateOther {

    private String nickName;

    private String headPortrait;

    private List<EvaluateImg> evaluateImgList;

    private Evaluate evaluate;

    public EvaluateOther() {
    }

    public EvaluateOther(String nickName, String headPortrait, List<EvaluateImg> evaluateImgList) {
        this.nickName = nickName;
        this.headPortrait = headPortrait;
        this.evaluateImgList = evaluateImgList;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public List<EvaluateImg> getEvaluateImgList() {
        return evaluateImgList;
    }

    public void setEvaluateImgList(List<EvaluateImg> evaluateImgList) {
        this.evaluateImgList = evaluateImgList;
    }

    public Evaluate getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
    }

    @Override
    public String toString() {
        return "EvaluateOther{" +
                "nickName='" + nickName + '\'' +
                ", headPortrait='" + headPortrait + '\'' +
                ", evaluateImgList=" + evaluateImgList +
                ", evaluate=" + evaluate +
                '}';
    }
}
