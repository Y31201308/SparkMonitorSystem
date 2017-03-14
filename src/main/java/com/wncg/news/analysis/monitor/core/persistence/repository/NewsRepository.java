package com.wncg.news.analysis.monitor.core.persistence.repository;

import com.wncg.news.analysis.monitor.web.model.NewsDetails;

import java.util.List;

public interface NewsRepository{

    List<NewsDetails> queryNews(int pageSize, int pageNum);

    Long queryNewsCount();

    void deleteNews(NewsDetails newsDetails);

    void updateNews(NewsDetails old, NewsDetails news);

    void addNews(NewsDetails newsDetails);

}
