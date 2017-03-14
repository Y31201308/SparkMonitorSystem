package com.wncg.news.analysis.monitor.core.persistence.service;

import com.wncg.news.analysis.monitor.core.event.Request;
import com.wncg.news.analysis.monitor.core.event.Response;

public interface NewsService {

    Response queryNews(Request request);

}
