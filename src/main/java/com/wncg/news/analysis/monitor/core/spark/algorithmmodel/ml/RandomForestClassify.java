package com.wncg.news.analysis.monitor.core.spark.algorithmmodel.ml;

import org.apache.spark.ml.classification.ClassificationModel;
import org.apache.spark.ml.classification.RandomForestClassificationModel;
import org.apache.spark.ml.classification.RandomForestClassifier;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

@Component
public class RandomForestClassify implements ClassifyModel {

    @Override
    public ClassificationModel classifyModel(Dataset<Row> data) {
        // Train a RandomForest model.
        RandomForestClassifier rf = new RandomForestClassifier()
                .setLabelCol("indexedLabel");

        RandomForestClassificationModel model = rf.fit(data);
        return model;
    }

    @Override
    public double modelEvaluator(Dataset<Row> data, ClassificationModel model) {
        // Make predictions.
        Dataset<Row> predictions = model.transform(data);

        // Select example rows to display.
        predictions.select("predictedLabel", "label", "features").show(5);

        // Select (prediction, true label) and compute test error
        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
                .setLabelCol("indexedLabel")
                .setPredictionCol("prediction")
                .setMetricName("accuracy");

        double accuracy = evaluator.evaluate(predictions);
        System.out.println("Test Error = " + (1.0 - accuracy));

        System.out.println("Learned classification forest model:\n" + ((RandomForestClassificationModel)model).toDebugString());
        return 0;
    }
}
