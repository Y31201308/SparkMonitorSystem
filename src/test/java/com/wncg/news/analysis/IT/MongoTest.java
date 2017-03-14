package com.wncg.news.analysis.IT;

import com.wncg.news.analysis.monitor.web.config.MonitorSysSpringBootApplication;
import com.wncg.news.analysis.monitor.web.model.NewsDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MonitorSysSpringBootApplication.class)
@TestPropertySource(locations="classpath:test.application.properties")
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void mongoCriteriaTest(){
        Query query = new Query();
        query.addCriteria(where("_id").in("http://3g.163.com/news/17/0302/11/CEH5NISG0001899O_0.html",
                "http://3g.163.com/news/17/0302/07/CEGP99DH000189FH_0.html"));
//        query.skip(54*10).limit(10);
        List<NewsDetails> list = mongoTemplate.find(query , NewsDetails.class);
        assertEquals(2 , list.size());
    }

    @Test
    public void mongoPageTest(){
        PageRequest pageRequest = new PageRequest(0, 10);

        List<NewsDetails> newsDetailsList = mongoTemplate.findAll(NewsDetails.class);
    }

    @Test
    public void mongoSkipTest(){
        Query query = new Query();
//        query.with(new Sort(Sort.Direction.ASC , ""));
        query.skip(0).limit(10);
        List<NewsDetails> list = mongoTemplate.find(query , NewsDetails.class);
        assertEquals(10 , list.size());
    }


    @Test
    public void mongoConnectionTest(){
        Query query = new Query();
//        query.with(new Sort(Sort.Direction.ASC , ""));
        query.skip(54*10).limit(10);
        List<NewsDetails> list = mongoTemplate.find(query , NewsDetails.class);
        assertEquals(2 , list.size());

//        NewsDetails newsDetails = mongoTemplate.findById("http://3g.163.com/news/17/0224/17/CE2BK7NU000187VE_0.html" , NewsDetails.class);
//        System.out.println(newsDetails);

//        DBCollection dbCollection = mongoTemplate.getCollection("NewsDetails");
//        DBCursor cursor = dbCollection.find();
//        while(cursor.hasNext()){
//            System.out.println(cursor.next());
//        }
//        System.out.println("news count:" + cursor.count());


        List<NewsDetails> newsDetailsList = mongoTemplate.findAll(NewsDetails.class);

        for (NewsDetails news : newsDetailsList){
            System.out.println("news: " + news);
        }
    }

//    @Test
//    public void mongoPagingTest(){
//        PageRequest pageRequest = new PageRequest(0 , 10);
//        Page<NewsDetails> detailsPage = newsPageDao.findAll(pageRequest);
//        System.out.println(detailsPage.getTotalPages());
//    }

}
