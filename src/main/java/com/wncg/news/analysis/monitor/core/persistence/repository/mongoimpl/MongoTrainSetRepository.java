package com.wncg.news.analysis.monitor.core.persistence.repository.mongoimpl;

import com.wncg.news.analysis.monitor.core.persistence.repository.TrainSetRepository;
import com.wncg.news.analysis.monitor.web.model.TrainingSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "trainSetRepository")
public class MongoTrainSetRepository implements TrainSetRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<TrainingSet> queryTrainSet(int pageSize, int pageNum) {
        Query query = new Query();
        query.skip(pageSize * pageNum).limit(pageSize);
        List<TrainingSet> list = mongoTemplate.find(query , TrainingSet.class);
        return list;
    }

    @Override
    public Long queryTrainSetCount() {
        return mongoTemplate.count(new Query() , "TrainingSet");
    }

    @Override
    public void updateTrainSet(TrainingSet oldTrainSet, TrainingSet newTrainSet) {

    }

    @Override
    public void deleteTrainSet(TrainingSet trainingSet) {

    }

    @Override
    public void addTrainSet(TrainingSet trainingSet) {

    }

}
