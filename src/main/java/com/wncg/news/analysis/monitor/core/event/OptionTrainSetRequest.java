package com.wncg.news.analysis.monitor.core.event;

import com.wncg.news.analysis.monitor.web.model.TrainSet;

public class OptionTrainSetRequest {

    private String trainSetId;
    private TrainSet trainSet;

    public TrainSet getTrainSet() {
        return trainSet;
    }

    public void setTrainSet(TrainSet trainSet) {
        this.trainSet = trainSet;
    }

    public String getTrainSetId() {
        return trainSetId;
    }

    public void setTrainSetId(String trainSetId) {
        this.trainSetId = trainSetId;
    }
}
