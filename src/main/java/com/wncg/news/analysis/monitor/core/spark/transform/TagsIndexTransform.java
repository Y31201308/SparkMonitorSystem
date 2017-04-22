package com.wncg.news.analysis.monitor.core.spark.transform;

import org.apache.spark.ml.feature.IndexToString;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

@Component
public class TagsIndexTransform {

    public StringIndexerModel stringIndexer(Dataset<Row> data){

        StringIndexerModel indexer = new StringIndexer()
                .setInputCol("label")
                .setOutputCol("indexedLabel")
                .fit(data);

        return indexer;
    }

    public Dataset<Row> indexToString(Dataset<Row> data, StringIndexerModel indexer){
        IndexToString converter = new IndexToString()
                .setInputCol("prediction")
                .setOutputCol("predictedLabel")
                .setLabels(indexer.labels());

        Dataset<Row> converted = converter.transform(data);

        return converted;
    }

}
