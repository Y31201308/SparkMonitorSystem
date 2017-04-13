package com.wncg.news.analysis.UT;

import com.wncg.news.analysis.monitor.core.event.AccidentType;
import org.junit.Test;

import java.util.Date;

public class AccidentEnumTypeTest {

    @Test
    public void enumValueTest(){
        AccidentType accidentType = AccidentType.NOT_ACCIDENT;
        System.out.println(accidentType.getMeaning());
        System.out.println(accidentType.getLabelTypeId());

        AccidentType[] accidentTypeArray = AccidentType.values();
        for (AccidentType accidentType1 : accidentTypeArray){
            System.out.println(accidentType1);
        }

    }

    @Test
    public void dataTimeTest(){
        System.out.println(new Date());
    }

}
