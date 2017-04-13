package com.wncg.news.analysis.monitor.core.persistence.repository.mongoimpl;

import com.wncg.news.analysis.monitor.core.matadata.RequestFailedException;
import com.wncg.news.analysis.monitor.core.persistence.repository.NewsRepository;
import com.wncg.news.analysis.monitor.web.model.NewsDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "newsRepository")
public class MongoNewsRepository implements NewsRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<NewsDetails> queryNewsByPage(int pageSize, int pageNum) {
        Query query = new Query();
        query.skip(pageSize * pageNum)
                .with(new Sort(new Sort.Order(Sort.Direction.DESC,"ptime")))
                .limit(pageSize);
        return mongoTemplate.find(query , NewsDetails.class);
    }

    @Override
    public NewsDetails queryNewsById(String newsUrl) {
        Query query = new Query();
        if (StringUtils.isBlank(newsUrl))
            throw new RequestFailedException("新闻链接不能为空");
        query.addCriteria(Criteria.where("newsUrl").is(newsUrl));
        NewsDetails newsDetails = mongoTemplate.findOne(query , NewsDetails.class);
        return newsDetails;
    }

    @Override
    public Long queryNewsCount() {
        Query query = new Query();
        return mongoTemplate.count(query , "NewsDetails");
    }

    @Override
    public void deleteNews(NewsDetails newsDetails) {
        mongoTemplate.remove(newsDetails);
    }

    @Override
    public NewsDetails updateNews(NewsDetails old, NewsDetails news) {
        Query query = new Query();
        query.addCriteria(Criteria.where("newsUrl").is(old.getNewsUrl()));

        Update update = new Update();
        if (!old.equals(news)){
            if (!old.getNewsUrl().equals(news.getNewsUrl()))
                update.set("newsUrl", news.getNewsUrl());
            if (!old.getNewsTitle().equals(news.getNewsTitle()))
                update.set("newsTitle", news.getNewsTitle());
            if (!old.getPureTitle().equals(news.getPureTitle()))
                update.set("pureTitle", news.getPureTitle());
            if (!old.getKeyWords().equals(news.getKeyWords()))
                update.set("keyWords", news.getKeyWords());
            if (!old.getContent().equals(news.getContent()))
                update.set("content", news.getContent());
            if (!old.getPtime().equals(news.getPtime()))
                update.set("ptime", news.getPtime());
        }

        NewsDetails result = mongoTemplate.findAndModify(query, update,
                FindAndModifyOptions.options().upsert(false).returnNew(true),
                NewsDetails.class
        );
        return result;
    }

    @Override
    public void addNews(NewsDetails newsDetails) {

    }

    @Override
    public void insertNews(NewsDetails newsDetails) {
        try {
            mongoTemplate.insert(newsDetails);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
