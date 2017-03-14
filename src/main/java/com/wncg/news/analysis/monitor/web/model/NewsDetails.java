package com.wncg.news.analysis.monitor.web.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "NewsDetails")
public class NewsDetails implements Serializable {

    @Id
    private String _id;
    private String title;
    private String pure_title;
    private String key_words;
    private String contents;
    private String ptime;

    public NewsDetails(String _id, String title, String pure_title, String key_words, String contents, String ptime) {
        this._id = _id;
        this.title = title;
        this.pure_title = pure_title;
        this.key_words = key_words;
        this.contents = contents;
        this.ptime = ptime;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPure_title() {
        return pure_title;
    }

    public void setPure_title(String pure_title) {
        this.pure_title = pure_title;
    }

    public String getKey_words() {
        return key_words;
    }

    public void setKey_words(String key_words) {
        this.key_words = key_words;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    @Override
    public String toString() {
        return "NewsDetails{" +
                "_id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", pure_title='" + pure_title + '\'' +
                ", key_words='" + key_words + '\'' +
                ", contents='" + contents + '\'' +
                ", ptime='" + ptime + '\'' +
                '}';
    }
}
