package com.wncg.news.analysis.monitor.core.spark.algorithmmodel;

import com.wncg.news.analysis.monitor.core.persistence.repository.TrainSetRepository;
import com.wncg.news.analysis.monitor.web.model.TrainSet;
import org.apache.spark.ml.feature.Word2Vec;
import org.apache.spark.ml.feature.Word2VecModel;
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
public class Word2VectorizerModel implements VectorModel {
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
        Dataset<Row> documentDF = spark.createDataFrame(data, schema);

        // Learn a mapping from words to Vectors.
        Word2Vec word2Vec = new Word2Vec()
                .setInputCol("sentence")
                .setOutputCol("result")
                .setVectorSize(3)
                .setMinCount(0);

        Word2VecModel model = word2Vec.fit(documentDF);
        Dataset<Row> featurizedData = model.transform(documentDF);
        featurizedData.show(false);

        return featurizedData;
    }
}
