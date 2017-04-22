package com.wncg.news.analysis.monitor.core.spark.algorithmmodel.ml;

import org.apache.spark.ml.classification.ClassificationModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface ClassifyModel {

    ClassificationModel classifyModel(Dataset<Row> data);

    double modelEvaluator(Dataset<Row> data, ClassificationModel model);

}
