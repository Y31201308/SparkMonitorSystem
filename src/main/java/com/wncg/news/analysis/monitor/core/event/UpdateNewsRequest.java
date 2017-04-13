package com.wncg.news.analysis.monitor.core.event;

import com.wncg.news.analysis.monitor.web.model.NewsDetails;

public class UpdateNewsRequest {

    private NewsDetails newsDetails;

    public NewsDetails getNewsDetails() {
        return newsDetails;
    }

    public void setNewsDetails(NewsDetails newsDetails) {
        this.newsDetails = newsDetails;
    }
}
