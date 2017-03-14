package com.wncg.news.analysis.monitor.core.persistence.repository.mongoimpl;

import com.wncg.news.analysis.monitor.core.persistence.repository.NewsRepository;
import com.wncg.news.analysis.monitor.web.model.NewsDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "newsRepository")
public class MongoNewsRepository implements NewsRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<NewsDetails> queryNews(int pageSize, int pageNum) {
        Query query = new Query();
        query.skip(pageSize * pageNum).limit(pageSize);
        return mongoTemplate.find(query , NewsDetails.class);
    }

    @Override
    public Long queryNewsCount() {
        Query query = new Query();
        return mongoTemplate.count(query , "NewsDetails");
    }

    @Override
    public void deleteNews(NewsDetails newsDetails) {

    }

    @Override
    public void updateNews(NewsDetails old, NewsDetails news) {

    }

    @Override
    public void addNews(NewsDetails newsDetails) {

    }

}
