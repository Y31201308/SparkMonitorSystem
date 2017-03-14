package com.wncg.news.analysis.monitor.core.persistence.repository;

import com.wncg.news.analysis.monitor.web.model.TrainingSet;

import java.util.List;

public interface TrainSetRepository {

    List<TrainingSet> queryTrainSet(int pageSize, int pageNum);

    Long queryTrainSetCount();

    void updateTrainSet(TrainingSet oldTrainSet, TrainingSet newTrainSet);

    void deleteTrainSet(TrainingSet trainingSet);

    void addTrainSet(TrainingSet trainingSet);

}
