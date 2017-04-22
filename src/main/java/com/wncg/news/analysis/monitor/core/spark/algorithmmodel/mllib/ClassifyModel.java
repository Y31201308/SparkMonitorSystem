package com.wncg.news.analysis.monitor.core.spark.algorithmmodel.mllib;

import org.apache.spark.mllib.classification.ClassificationModel;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.rdd.RDD;

public interface ClassifyModel {

    ClassificationModel classifyModel(RDD<LabeledPoint> data);

    double modelEvaluator(RDD<LabeledPoint> data, ClassificationModel model);

}
