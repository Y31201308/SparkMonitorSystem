package com.wncg.news.analysis.monitor.core.spark.algorithmmodel.mllib;

import org.apache.spark.mllib.classification.ClassificationModel;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.rdd.RDD;
import org.springframework.stereotype.Component;

@Component
public class SVMClassify implements ClassifyModel {

    @Override
    public ClassificationModel classifyModel(RDD<LabeledPoint> data) {
        // stepSize（迭代步伐大小），regParam（regularization正则化控制参数）
        // miniBatchFraction（每次迭代参与计算的样本比例），initialWeights（weight向量初始值）等参数可以进行设置
        int numIterations = 1000;   //设置迭代次数为1000
        //SVMWithSGD.train() 方法默认的通过把正则化参数设为1来执行来范数
        SVMModel model = SVMWithSGD.train(data, numIterations);
        return model;
    }

    @Override
    public double modelEvaluator(RDD<LabeledPoint> data, ClassificationModel model) {
        return 0;
    }
}
