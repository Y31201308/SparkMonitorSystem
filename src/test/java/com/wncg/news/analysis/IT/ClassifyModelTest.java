package com.wncg.news.analysis.IT;

import com.wncg.news.analysis.monitor.core.spark.algorithmmodel.ml.NaiveBayesClassify;
import com.wncg.news.analysis.monitor.core.spark.algorithmmodel.ml.RandomForestClassify;
import com.wncg.news.analysis.monitor.core.spark.transform.StammerParticiple;
import com.wncg.news.analysis.monitor.core.spark.transform.TagsIndexTransform;
import com.wncg.news.analysis.monitor.core.spark.vectors.TFIDFModel;
import com.wncg.news.analysis.monitor.web.config.MonitorSysSpringBootApplication;
import org.apache.spark.ml.classification.ClassificationModel;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Before;
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
public class ClassifyModelTest {

    @Value("${hadoop.home.name}")
    private String hadoopHomeName;

    @Value("${hadoop.home.dir}")
    private String homeDirValue;

    @Autowired
    private TagsIndexTransform tagsIndexTransform;

    @Autowired
    private NaiveBayesClassify classifyModel;

    @Autowired
    private RandomForestClassify forestClassify;

    @Autowired
    private TFIDFModel tfidfModel;

    @Autowired
    private StammerParticiple participle;

    @Before
    public void before(){
        System.setProperty(hadoopHomeName, homeDirValue);
    }

    @Test
    public void naiveBayesClassifyTest(){
        SparkSession spark = SparkSession.builder()
                .master("local[*]").appName("JavaWordCount").getOrCreate();

        List<Row> data = tfidfModel.dataTransformToRDD(participle);
        Dataset<Row> featureData = tfidfModel.vectorTransform(spark, data);
        featureData.show(false);

        StringIndexerModel siModel = tagsIndexTransform.stringIndexer(featureData);
        Dataset<Row> indexerData = siModel.transform(featureData);

        indexerData.show(false);

        ClassificationModel model = classifyModel.classifyModel(indexerData);

        Dataset<Row> afterClassifyData = model.transform(featureData);
        afterClassifyData.show(false);
        afterClassifyData.select("label", "prediction").show(false);

        Dataset<Row> rows = tagsIndexTransform.indexToString(afterClassifyData, siModel);
        rows.show(false);

        indexerData.select("label", "indexedLabel").show(false);
        rows.select("label", "prediction" , "predictedLabel").show(false);


        classifyModel.modelEvaluator(indexerData, model);
    }

    @Test
    public void fandomForestClassifyTest(){
        SparkSession spark = SparkSession.builder()
                .master("local[*]").appName("JavaWordCount").getOrCreate();

        List<Row> data = tfidfModel.dataTransformToRDD(participle);
        Dataset<Row> featureData = tfidfModel.vectorTransform(spark, data);
        featureData.show(false);

        StringIndexerModel siModel = tagsIndexTransform.stringIndexer(featureData);
        Dataset<Row> indexerData = siModel.transform(featureData);
        indexerData.show(false);

        ClassificationModel model = forestClassify.classifyModel(indexerData);

        Dataset<Row> afterClassifyData = model.transform(featureData);
        Dataset<Row> rows = tagsIndexTransform.indexToString(afterClassifyData, siModel);
        rows.show(false);

        indexerData.select("label", "indexedLabel").show(false);
        rows.select("label", "prediction" , "predictedLabel").show(false);

        classifyModel.modelEvaluator(indexerData, model);
    }
}
