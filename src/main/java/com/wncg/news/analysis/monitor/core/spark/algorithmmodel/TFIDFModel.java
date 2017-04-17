package com.wncg.news.analysis.monitor.core.spark.algorithmmodel;

import com.wncg.news.analysis.monitor.core.persistence.repository.TrainSetRepository;
import com.wncg.news.analysis.monitor.web.model.TrainSet;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.IDF;
import org.apache.spark.ml.feature.IDFModel;
import org.apache.spark.ml.feature.Tokenizer;
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
public class TFIDFModel implements VectorModel {

    @Autowired
    private TrainSetRepository repository;

    @Override
    public List<Row> dataTransformToRDD(Participle participle) {
        List<Row> rowList = new ArrayList<>();
        List<TrainSet> trainSetList = repository.queryAllTrainSet();
        for (TrainSet trainSet : trainSetList){
            String sentence = participle.participle(trainSet.getContent());
            rowList.add(RowFactory.create(sentence, trainSet.getLabelLev()));
        }
        return rowList;
    }

    @Override
    public StructType structTypeSchema() {
        StructType schema = new StructType(new StructField[]{
                new StructField("sentence", DataTypes.StringType, false, Metadata.empty()),
                new StructField("label", DataTypes.IntegerType, false, Metadata.empty())
        });
        return schema;
    }

    @Override
    public Dataset<Row> vectorTransform(SparkSession spark, List<Row> data) {
        StructType schema = structTypeSchema();

        Dataset<Row> sentenceData = spark.createDataFrame(data, schema);

        Tokenizer tokenizer = new Tokenizer()
                .setInputCol("sentence")
                .setOutputCol("words");

        Dataset<Row> wordsData = tokenizer.transform(sentenceData);

        int numFeatures = 20;
        HashingTF hashingTF = new HashingTF()
                .setInputCol("words")
                .setOutputCol("rawFeatures")
                .setNumFeatures(numFeatures);

        Dataset<Row> featurizedData = hashingTF.transform(wordsData);
        featurizedData.show(false);

        // alternatively, CountVectorizer can also be used to get term frequency vectors
        IDF idf = new IDF().setInputCol("rawFeatures").setOutputCol("features");
        IDFModel idfModel = idf.fit(featurizedData);
        Dataset<Row> rescaledData = idfModel.transform(featurizedData);
        rescaledData.show(false);

        return rescaledData;
    }
}
