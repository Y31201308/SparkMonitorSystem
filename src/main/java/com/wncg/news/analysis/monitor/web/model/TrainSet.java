package com.wncg.news.analysis.monitor.web.model;

import com.wncg.news.analysis.monitor.core.event.AccidentType;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "TrainSet")
public class TrainSet implements Serializable{

    @Id
    private String id;

    @Indexed(unique = true)
    private String content;
    private Integer labelLev;
    private DateTime createTime;

    @Transient
    private AccidentType accidentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLabelLev() {
        if (labelLev == null)
            labelLev = accidentType.getLabelTypeId();
        return labelLev;
    }

    public void setLabelLev(Integer labelLev) {
        this.labelLev = labelLev;
    }

    public AccidentType getAccidentType() {
        if (accidentType == null)
            this.accidentType = AccidentType.getAccidentTypeById(this.labelLev);
        return accidentType;
    }

    public void setAccidentType(AccidentType accidentType) {
        this.accidentType = accidentType;
        labelLev = accidentType.getLabelTypeId();
    }

    @Override
    public String toString() {
        return "TrainSet{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", labelLev=" + labelLev +
                '}';
    }
}
