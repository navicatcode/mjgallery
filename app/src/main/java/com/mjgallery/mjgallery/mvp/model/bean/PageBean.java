package com.mjgallery.mjgallery.mvp.model.bean;

public class PageBean<T> {


    /**
     * pageIndex : 0
     * pageSize : 15
     * pageCount : 1
     * totalCount : 4
     */

    private int pageIndex;
    private int pageSize;
    private int pageCount;
    private int totalCount;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
