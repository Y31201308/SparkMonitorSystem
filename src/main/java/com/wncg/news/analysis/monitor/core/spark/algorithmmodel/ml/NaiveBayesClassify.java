package com.wncg.news.analysis.monitor.core.spark.algorithmmodel.ml;

import org.apache.spark.ml.classification.ClassificationModel;
import org.apache.spark.ml.classification.NaiveBayes;
import org.apache.spark.ml.classification.NaiveBayesModel;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

@Component
public class NaiveBayesClassify implements ClassifyModel {

    @Override
    public ClassificationModel classifyModel(Dataset<Row> data) {
        // create the trainer and set its parameters
        NaiveBayes nb = new NaiveBayes();

        // train the model
        final NaiveBayesModel model = nb.fit(data);
        return model;
    }

    @Override
    public double modelEvaluator(Dataset<Row> data, ClassificationModel model) {
        Dataset<Row> predictions = model.transform(data);
        predictions.show();

        // compute accuracy on the test set
        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
                .setLabelCol("indexedLabel")
                .setPredictionCol("prediction")
                .setMetricName("accuracy");

        double accuracy = evaluator.evaluate(predictions);
        System.out.println("Test set accuracy = " + accuracy);
        return accuracy;
    }
}
