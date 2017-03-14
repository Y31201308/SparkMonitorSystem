package com.wncg.news.analysis.monitor.core.springconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
@EnableAutoConfiguration
//@EnableMongoAuditing
//@EnableJpaRepositories
public class MongoDBConfig{

//    @Autowired
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoDBConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

}
