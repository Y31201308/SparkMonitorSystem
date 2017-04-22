package com.wncg.news.analysis.monitor.core.spark.vectors;

import com.wncg.news.analysis.monitor.core.spark.transform.Participle;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import java.util.List;

public interface VectorModel {

    List<Row> dataTransformToRDD(Participle participle);

    StructType structTypeSchema();

    Dataset<Row> vectorTransform(SparkSession spark, List<Row> data);
}
