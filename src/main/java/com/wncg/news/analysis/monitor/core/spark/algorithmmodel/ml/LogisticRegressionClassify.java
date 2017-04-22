package com.wncg.news.analysis.monitor.core.spark.algorithmmodel.ml;

import org.apache.spark.ml.classification.ClassificationModel;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

@Component
public class LogisticRegressionClassify implements ClassifyModel {

    @Override
    public ClassificationModel classifyModel(Dataset<Row> training){

        LogisticRegression lr = new LogisticRegression()
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);

        // Fit the model
        LogisticRegressionModel lrModel = lr.fit(training);

        // Print the coefficients and intercept for logistic regression
        System.out.println("Coefficients: "
                + lrModel.coefficients() + " Intercept: " + lrModel.intercept());

        /*// We can also use the multinomial family for binary classification
        LogisticRegression mlr = new LogisticRegression()
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8)
                .setFamily("multinomial");

        // Fit the model
        LogisticRegressionModel mlrModel = mlr.fit(training);*/
        return lrModel;
    }

    @Override
    public double modelEvaluator(Dataset<Row> data, ClassificationModel model) {
        return 0;
    }

}
