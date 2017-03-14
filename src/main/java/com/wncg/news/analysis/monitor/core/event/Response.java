package com.wncg.news.analysis.monitor.core.event;

import java.util.List;

public class Response<T> {

    private int pageSize;
    private int currentPageNum;
    private int totalCount;
    private int totalPage;
    private List<T> data;

    public Response() {
    }

    public Response(int pageSize, int currentPageNum, int totalCount, List<T> data) {
        this.pageSize = pageSize;
        this.currentPageNum = currentPageNum + 1;
        this.totalCount = totalCount;
        this.data = data;
        this.totalPage = (int) Math.ceil(1.0 * totalCount/pageSize);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalPage() {
        if(this.totalPage == 0)
            this.totalPage = (int) Math.ceil(1.0 * totalCount/pageSize);
        return this.totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum + 1;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        if(this.totalPage == 0)
            this.totalPage = (int) Math.ceil(1.0 * totalCount/pageSize);
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "pageSize=" + pageSize +
                ", currentPageNum=" + currentPageNum +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                '}';
    }
}
