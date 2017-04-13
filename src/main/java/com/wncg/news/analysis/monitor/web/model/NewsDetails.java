package com.wncg.news.analysis.monitor.web.model;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "NewsDetails")
public class NewsDetails{

    @Id
    private String newsUrl;
    private String newsTitle;
    private String pureTitle;
    private String keyWords;
    private String content;
    private String ptime;

    public NewsDetails(){}

    public NewsDetails(String newsUrl, String newsTitle, String pureTitle, String keyWords, String content, String ptime) {
        this.newsUrl = newsUrl;
        this.newsTitle = newsTitle;
        this.pureTitle = pureTitle;
        this.keyWords = keyWords;
        this.content = content;
        this.ptime = ptime;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getPureTitle() {
        return pureTitle;
    }

    public void setPureTitle(String pureTitle) {
        this.pureTitle = pureTitle;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (!(obj instanceof NewsDetails))
            return false;

        NewsDetails that = (NewsDetails) obj;

        return this.getNewsUrl().equals(that.getNewsUrl())
                && this.getNewsTitle().equals(that.getNewsTitle())
                && this.getPureTitle().equals(that.getPureTitle())
                && this.getKeyWords().equals(that.getKeyWords())
                && this.getContent().equals(that.getContent())
                && this.getPtime().equals(that.getPtime());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getNewsUrl())
                .append(getNewsTitle())
                .append(getPureTitle())
                .append(getKeyWords())
                .append(getContent())
                .append(getPtime())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "NewsDetails{" +
                "newsUrl='" + newsUrl + '\'' +
                ", newsTitle='" + newsTitle + '\'' +
                ", pureTitle='" + pureTitle + '\'' +
                ", keyWords='" + keyWords + '\'' +
                ", content='" + content + '\'' +
                ", ptime='" + ptime + '\'' +
                '}';
    }
}
