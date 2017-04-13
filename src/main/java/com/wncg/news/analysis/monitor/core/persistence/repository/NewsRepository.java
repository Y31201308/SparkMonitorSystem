package com.wncg.news.analysis.monitor.core.persistence.repository;

import com.wncg.news.analysis.monitor.web.model.NewsDetails;

import java.util.List;

public interface NewsRepository{

    List<NewsDetails> queryNewsByPage(int pageSize, int pageNum);

    NewsDetails queryNewsById(String newsUrl);

    Long queryNewsCount();

    void deleteNews(NewsDetails newsDetails);

    NewsDetails updateNews(NewsDetails old, NewsDetails news);

    void addNews(NewsDetails newsDetails);

    void insertNews(NewsDetails newsDetails);

}
