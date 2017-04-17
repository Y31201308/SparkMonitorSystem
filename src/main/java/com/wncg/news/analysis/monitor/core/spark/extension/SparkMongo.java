package com.wncg.news.analysis.monitor.core.spark.extension;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.bson.Document;

import java.util.List;

public class SparkMongo {
    public static final String mongo_uri = "mongodb://wncg:123456@127.0.0.1/news_data.NewsDetails";

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "E:\\hadoop-2.7.3");
        SparkSession spark = SparkSession.builder()
                .master("local[*]").appName("MongoSparkConnectorIntro")
                .config("spark.mongodb.input.uri", mongo_uri)
                .config("spark.mongodb.output.uri", mongo_uri).getOrCreate();

        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        SQLContext sqlContext = new SQLContext(spark);

        JavaMongoRDD<Document> originalData = MongoSpark.load(jsc);

        JavaRDD<Row> words = originalData.map(new Function<Document, Row>() {

            @Override
            public Row call(Document document) throws Exception {
                System.out.println(document.toJson());
//                System.out.println("noPunTitle: " + noPunTitle);
                String words = jieBaAnalysis(document.get("newsTitle").toString());
//                System.out.println("words:" + words);
                return RowFactory.create(words);
            }
        });

        StructType schema = new StructType(new StructField[]{
                new StructField("text", DataTypes.StringType, false, Metadata.empty())
        });

        Dataset<Row> wordsData = sqlContext.createDataFrame(words.rdd(), schema);
        wordsData.show(false);
    }

    public static String jieBaAnalysis(String originalText){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> textList = segmenter.sentenceProcess(originalText);
        StringBuffer result = new StringBuffer();
        for(String word : textList){
            result.append(word + " ");
        }
        return result.toString().trim();
    }
}
