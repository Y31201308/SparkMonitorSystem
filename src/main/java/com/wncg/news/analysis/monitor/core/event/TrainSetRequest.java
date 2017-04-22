package com.wncg.news.analysis.monitor.core.event;

import com.wncg.news.analysis.monitor.web.model.TrainSet;

public class TrainSetRequest{

    public static final int DEFAULT_EVERY_PAGE_COUNT = 50;
    public static final int EVERY_PAGE_MAX_COUNT = 500;
    public static final int EVERY_PAGE_MIN_COUNT = 10;
    public static final int DEFAULT_PAGE_NUM = 1;

    private int pageSize = DEFAULT_EVERY_PAGE_COUNT;

    private int currentPageNum = DEFAULT_PAGE_NUM;

    private TrainSet trainSet;
    private int labelType;

    public TrainSetRequest() {

    }

    public TrainSetRequest(int pageSize, int pageNum) {
        if(pageSize < EVERY_PAGE_MIN_COUNT){
            this.pageSize = EVERY_PAGE_MIN_COUNT;
        }else if(pageSize > EVERY_PAGE_MAX_COUNT){
            this.pageSize = EVERY_PAGE_MAX_COUNT;
        }else{
            this.pageSize = pageSize;
        }

        if(pageNum < DEFAULT_PAGE_NUM){
            this.currentPageNum = 0;
        }else{
            this.currentPageNum = pageNum - 1;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public TrainSet getTrainSet() {
        return trainSet;
    }

    public void setTrainSet(TrainSet trainSet) {
        this.trainSet = trainSet;
    }

    public int getLabelType() {
        return labelType;
    }

    public void setLabelType(int labelType) {
        this.labelType = labelType;
    }
}
