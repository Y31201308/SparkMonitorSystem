package com.wncg.news.analysis.monitor.web.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TrainingSet")
public class TrainingSet {

    @Id
    private String _id;
    private String contents;
    private String labelLev;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getLabelLev() {
        return labelLev;
    }

    public void setLabelLev(String labelLev) {
        this.labelLev = labelLev;
    }
}
