package com.wncg.news.analysis.monitor.core.persistence.service;

import com.wncg.news.analysis.monitor.core.event.DeleteNewsRequest;
import com.wncg.news.analysis.monitor.core.event.QueryNewsRequest;
import com.wncg.news.analysis.monitor.core.event.Response;
import com.wncg.news.analysis.monitor.core.event.UpdateNewsRequest;

public interface NewsService {

    Response queryNewsByPage(QueryNewsRequest request);

    Response queryNewsById(QueryNewsRequest request);

    Response updateNews(UpdateNewsRequest request);

    Response deleteNews(DeleteNewsRequest request);
}
