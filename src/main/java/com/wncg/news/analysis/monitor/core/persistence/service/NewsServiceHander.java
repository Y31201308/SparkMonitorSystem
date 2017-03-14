package com.wncg.news.analysis.monitor.core.persistence.service;

import com.wncg.news.analysis.monitor.core.event.Request;
import com.wncg.news.analysis.monitor.core.event.Response;
import com.wncg.news.analysis.monitor.core.persistence.repository.NewsRepository;
import com.wncg.news.analysis.monitor.web.model.NewsDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceHander implements NewsService{

    @Autowired
    private NewsRepository newsRepository;


    @Override
    public Response queryNews(Request request) {
        List<NewsDetails> data = newsRepository.queryNews(request.getPageSize() , request.getCurrentPageNum());
        Response<NewsDetails> response = new Response<NewsDetails>();
        response.setData(data);
        response.setPageSize(request.getPageSize());
        response.setCurrentPageNum(request.getCurrentPageNum());
        response.setTotalCount(newsRepository.queryNewsCount().intValue());
        return response;
    }
}
