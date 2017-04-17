package com.wncg.news.analysis.monitor.core.spark.algorithmmodel;

import com.wncg.news.analysis.monitor.core.persistence.repository.TrainSetRepository;
import com.wncg.news.analysis.monitor.web.model.TrainSet;
import org.apache.spark.ml.feature.CountVectorizer;
import org.apache.spark.ml.feature.CountVectorizerModel;
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

/**
 * 旨在通过计数来将一个文档转换为向量
 */
@Component
public class CountvectorizerModel implements VectorModel {

    @Autowired
    private TrainSetRepository repository;

    @Override
    public List<Row> dataTransformToRDD(Participle participle) {
        List<Row> rowList = new ArrayList<>();
        List<TrainSet> trainSetList = repository.queryAllTrainSet();
        for (TrainSet trainSet : trainSetList){
            String sentence = participle.participle(trainSet.getContent());
            rowList.add(RowFactory.create(sentence.split(" "), trainSet.getLabelLev()));
        }
        return rowList;
    }

    @Override
    public StructType structTypeSchema() {
        StructType schema = new StructType(new StructField[]{
                new StructField("sentence", DataTypes.createArrayType(DataTypes.StringType), false, Metadata.empty()),
                new StructField("label", DataTypes.IntegerType, false, Metadata.empty())
        });
        return schema;
    }

    @Override
    public Dataset<Row> vectorTransform(SparkSession spark, List<Row> data) {
        StructType schema = structTypeSchema();
        Dataset<Row> df = spark.createDataFrame(data, schema);

        // fit a CountVectorizerModel from the corpus
        CountVectorizerModel cvModel = new CountVectorizer()
                .setInputCol("sentence")
                .setOutputCol("feature")
                .setVocabSize(3)
                .setMinDF(2)
                .fit(df);

        Dataset<Row> featurizedData = cvModel.transform(df);
        featurizedData.show(false);

        return featurizedData;
    }
}
