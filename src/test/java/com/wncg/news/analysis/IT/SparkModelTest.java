package com.wncg.news.analysis.IT;

import com.wncg.news.analysis.monitor.core.spark.algorithmmodel.SVMClassifyModel;
import com.wncg.news.analysis.monitor.web.config.MonitorSysSpringBootApplication;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MonitorSysSpringBootApplication.class)
@TestPropertySource(locations="classpath:test.application.properties")
public class SparkModelTest {

    @Autowired
    private SVMClassifyModel svmClassifyModel;

    @Test
    public void ClassifyModelTest(){
        System.setProperty("hadoop.home.dir", "E:\\hadoop-2.7.3");
        SparkSession spark = SparkSession.builder()
                .master("local[*]").appName("JavaWordCount").getOrCreate();

        List<Row> list = svmClassifyModel.getTrainSetRDD();
        StructType schema = new StructType(new StructField[]{
                new StructField("text", DataTypes.StringType, false, Metadata.empty()),
                new StructField("label", DataTypes.IntegerType, false, Metadata.empty())
        });

        Dataset<Row> training = spark.createDataFrame(list, schema);
        training.show();
    }

    @Test
    public void SVMClassifyTest(){
        svmClassifyModel.SVMClassify();
    }


}
