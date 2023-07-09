package com.zsycx.commons;

/**
 * @ClassName: PageBean
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */

/**
 * 分页实体封装类
 *
 * @param <T> 具体分页数据封装对象
 */
public class PageBean<T> {
    private Long pageNo;
    private Long pageSize;
    private Long totalNum;
    private Long pageCount;
    private Long skipNum;
    private T data;

    public PageBean(Long pageNo, Long pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.skipNum = (pageNo - 1) * pageSize;
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {

        this.totalNum = totalNum;
        if (totalNum == null || totalNum == 0 || pageSize == null || pageSize == 0) {
            this.pageCount = 0L;
            return;
        }
        this.pageCount = totalNum / pageSize;
        if (totalNum % pageSize > 0) {
            this.pageCount++;
        }
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public Long getSkipNum() {
        return skipNum;
    }

    public void setSkipNum(Long skipNum) {
        this.skipNum = skipNum;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", totalNum=" + totalNum +
                ", pageCount=" + pageCount +
                ", skipNum=" + skipNum +
                ", data=" + data +
                '}';
    }
}
