package com.wncg.news.analysis.monitor.core.persistence.repository.mongoimpl;

import com.mongodb.WriteResult;
import com.wncg.news.analysis.monitor.core.persistence.repository.TrainSetRepository;
import com.wncg.news.analysis.monitor.web.model.TrainSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "trainSetRepository")
public class MongoTrainSetRepository implements TrainSetRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<TrainSet> queryTrainSetByPage(int pageSize, int pageNum) {
        Query query = new Query();
        query.skip(pageSize * pageNum)
                .with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime")))
                .limit(pageSize);
        List<TrainSet> list = mongoTemplate.find(query , TrainSet.class);
        return list;
    }

    @Override
    public TrainSet queryTrainSetById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        TrainSet trainSet = mongoTemplate.findOne(query , TrainSet.class);
        return trainSet;
    }

    @Override
    public Long queryTrainSetCount() {
        return mongoTemplate.count(new Query() , "TrainSet");
    }

    @Override
    public TrainSet updateTrainSet(TrainSet old, TrainSet news) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(old.getId()));

        Update update = new Update();
        update.set("content", news.getContent());
        update.set("labelLev", news.getLabelLev());

        TrainSet result = mongoTemplate.findAndModify(query, update,
                FindAndModifyOptions.options().upsert(false).returnNew(true),
                TrainSet.class
        );
        return result;
    }

    @Override
    public void deleteTrainSet(TrainSet trainSet) {
        WriteResult writeResult = mongoTemplate.remove(trainSet);
    }

    @Override
    public void addTrainSet(TrainSet trainSet) {
        mongoTemplate.insert(trainSet);
    }

    @Override
    public List<TrainSet> queryAllTrainSet() {
        return mongoTemplate.findAll(TrainSet.class);
    }

}
