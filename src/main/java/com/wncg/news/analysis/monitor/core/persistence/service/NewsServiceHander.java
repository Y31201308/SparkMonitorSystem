package com.wncg.news.analysis.monitor.core.persistence.service;

import com.wncg.news.analysis.monitor.core.event.DeleteNewsRequest;
import com.wncg.news.analysis.monitor.core.event.QueryNewsRequest;
import com.wncg.news.analysis.monitor.core.event.Response;
import com.wncg.news.analysis.monitor.core.event.UpdateNewsRequest;
import com.wncg.news.analysis.monitor.core.matadata.RequestFailedException;
import com.wncg.news.analysis.monitor.core.persistence.repository.NewsRepository;
import com.wncg.news.analysis.monitor.web.model.NewsDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceHander implements NewsService{

    @Autowired
    private NewsRepository newsRepository;


    @Override
    public Response queryNewsByPage(QueryNewsRequest request) {
        List<NewsDetails> data = newsRepository.queryNewsByPage(request.getPageSize() , request.getCurrentPageNum());
        Response<NewsDetails> response = new Response<NewsDetails>();
        response.setData(data);
        response.setPageSize(request.getPageSize());
        response.setCurrentPageNum(request.getCurrentPageNum());
        response.setTotalCount(newsRepository.queryNewsCount().intValue());
        return response;
    }

    @Override
    public Response queryNewsById(QueryNewsRequest request) {
        if(request.getNewsUrl() == null)
            throw new RequestFailedException("新闻查询错误，未接收到新闻url");

        NewsDetails newsDetails = newsRepository.queryNewsById(request.getNewsUrl());
        Response<NewsDetails> response = new Response<NewsDetails>();
        List<NewsDetails> newsDetailsList = new ArrayList<>(1);
        newsDetailsList.add(newsDetails);
        response.setData(newsDetailsList);
        return response;
    }

    @Override
    public Response updateNews(UpdateNewsRequest request) {
        NewsDetails newsDetails = request.getNewsDetails();
        NewsDetails oldNews = null;
        Response response = new Response();
        try {
            oldNews = newsRepository.queryNewsById(newsDetails.getNewsUrl());
            if (request.getNewsDetails().equals(oldNews))
                throw new RequestFailedException("未做任何改变");

            NewsDetails result = newsRepository.updateNews(oldNews, newsDetails);

            if (result.equals(oldNews))
                throw new RequestFailedException("修改新闻失败");

        }catch (RequestFailedException e){
            response.fail(e.getMessage());
        }catch (Exception e){
            response.fail(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response deleteNews(DeleteNewsRequest request) {
        Response response = new Response();
        try {
            NewsDetails deleteNews = newsRepository.queryNewsById(request.getNewsUrl());
            newsRepository.deleteNews(deleteNews);
        }catch (RequestFailedException e){
            response.fail(e.getMessage());
        }catch (Exception e){
            response.fail(e.getMessage());
        }
        return response;
    }
}
