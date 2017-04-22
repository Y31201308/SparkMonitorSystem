package com.wncg.news.analysis.IT;

import com.wncg.news.analysis.monitor.core.spark.algorithmmodel.ml.NaiveBayesClassify;
import com.wncg.news.analysis.monitor.core.spark.transform.StammerParticiple;
import com.wncg.news.analysis.monitor.core.spark.transform.TagsIndexTransform;
import com.wncg.news.analysis.monitor.core.spark.vectors.CountvectorizerModel;
import com.wncg.news.analysis.monitor.core.spark.vectors.TFIDFModel;
import com.wncg.news.analysis.monitor.core.spark.vectors.Word2VectorizerModel;
import com.wncg.news.analysis.monitor.web.config.MonitorSysSpringBootApplication;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MonitorSysSpringBootApplication.class)
@TestPropertySource(locations="classpath:test.application.properties")
public class VectorModelTest {

    @Autowired
    private TFIDFModel model;

    @Autowired
    public CountvectorizerModel countvectorizerModel;

    @Autowired
    private Word2VectorizerModel word2VectorizerModel;

    @Autowired
    private StammerParticiple participle;

    @Value("${hadoop.home.name}")
    private String hadoopHomeName;

    @Value("${hadoop.home.dir}")
    private String homeDirValue;

    @Autowired
    private TagsIndexTransform tagsIndexTransform;

    @Autowired
    private NaiveBayesClassify classifyModel;

    @BeforeClass
    public void before(){
        System.setProperty(hadoopHomeName, homeDirValue);
    }

    @Test
    public void TFIDFModelTest(){
        SparkSession spark = SparkSession.builder()
                .master("local[*]").appName("JavaWordCount").getOrCreate();

        List<Row> data = model.dataTransformToRDD(participle);
        Dataset<Row> featureData = model.vectorTransform(spark, data);
        Dataset<Row> indexerData = tagsIndexTransform.stringIndexer(featureData).transform(featureData);
        indexerData.show(false);

    }

    @Test
    public void CountvectorizerModelTest(){
        System.setProperty(hadoopHomeName, homeDirValue);
        SparkSession spark = SparkSession.builder()
                .master("local[*]").appName("JavaWordCount").getOrCreate();

        List<Row> data = countvectorizerModel.dataTransformToRDD(participle);
        countvectorizerModel.vectorTransform(spark, data);
    }

    @Test
    public void Word2VectorizerModelTest(){
        SparkSession spark = SparkSession.builder()
                .master("local[*]").appName("JavaWordCount").getOrCreate();

        List<Row> data = word2VectorizerModel.dataTransformToRDD(participle);
        Dataset<Row> featureData = word2VectorizerModel.vectorTransform(spark, data);
        Dataset<Row> indexerData = tagsIndexTransform.stringIndexer(featureData).transform(featureData);
        indexerData.show(false);

        indexerData.select("features", "categoryIndex").foreach(row -> {
            row.get(0);
            row.get(1);
        });
//        RDD<LabeledPoint> labeledPointRDD = indexerData.select("features", "categoryIndex").rdd();

    }

}
