package com.wncg.news.analysis.monitor.core.spark.algorithmmodel;


import com.wncg.news.analysis.monitor.core.persistence.repository.TrainSetRepository;
import com.wncg.news.analysis.monitor.web.model.TrainSet;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SVMClassifyModel implements ClassifyModel{

    @Autowired
    private TrainSetRepository repository;

    public List<Row> getTrainSetRDD(){
        List<Row> rowList = new ArrayList<>();
        List<TrainSet> trainSetList = repository.queryAllTrainSet();
        for (TrainSet trainSet : trainSetList){
            rowList.add(RowFactory.create(trainSet.getContent(), trainSet.getLabelLev()));
        }
        return rowList;
    }

    public void SVMClassify(){

        System.setProperty("hadoop.home.dir", "E:\\hadoop-2.7.3");
        SparkSession spark = SparkSession.builder()
                .master("local[*]").appName("JavaWordCount").getOrCreate();

        List<Row> list = getTrainSetRDD();
        StructType schema = new StructType(new StructField[]{
                new StructField("text", DataTypes.StringType, false, Metadata.empty()),
                new StructField("label", DataTypes.IntegerType, false, Metadata.empty())
        });

        Dataset<Row> training = spark.createDataFrame(list, schema);
        training.show();
    }



}
