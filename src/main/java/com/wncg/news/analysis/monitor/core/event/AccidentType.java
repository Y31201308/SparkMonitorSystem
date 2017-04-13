package com.wncg.news.analysis.monitor.core.event;

import com.wncg.news.analysis.monitor.core.matadata.RequestFailedException;

public enum AccidentType {

    NOT_ACCIDENT("非事故", 0),
    FIRE("消防事故", 1),
    SECURITY("治安事故", 2),
    CRIMINAL("刑事事故", 3),
    TRAFFIC("交通事故", 4),
    FOOD_DRUG_ENVIRONMENT("食药环事故", 5);

    private String meaning;
    private Integer labelTypeId;

    AccidentType(String meaning, int labelTypeId) {
        this.meaning = meaning;
        this.labelTypeId = labelTypeId;
    }

    public String getMeaning() {
        return meaning;
    }

    public Integer getLabelTypeId() {
        return labelTypeId;
    }

    public static AccidentType getAccidentTypeByName(String name){
        AccidentType[] typeArray = AccidentType.values();
        AccidentType accidentType = null;
        for (AccidentType type : typeArray){
            if (name.equals(type.name())) {
                accidentType = type;
            }
        }
        if (accidentType == null)
            throw new RequestFailedException("枚举类型参数错误：" + name);

        return accidentType;
    }

    public static AccidentType getAccidentTypeById(int labelTypeId){

        if (labelTypeId > 5 || labelTypeId < 0)
            throw new RequestFailedException("枚举类型参数错误：" + labelTypeId);

        AccidentType[] typeArray = AccidentType.values();
        AccidentType accidentType = null;
        for (AccidentType type : typeArray){
            if (labelTypeId == type.getLabelTypeId()) {
                accidentType = type;
            }
        }
        return accidentType;
    }
}
