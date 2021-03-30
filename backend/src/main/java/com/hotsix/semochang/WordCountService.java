package com.hotsix.semochang;

import com.hotsix.semochang.model.RecommendDto;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.spark.sql.*;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.evaluation.ClusteringEvaluator;
import org.apache.spark.ml.linalg.Vector;

/**
 * author: pinest94
 * since: 2021-03-25
 */
@Service
public class WordCountService {

    @Autowired
    JavaSparkContext sc;

    @Autowired
    SparkSession sparkSession;

    static final String PATH = "C:\\Users\\multicampus\\Desktop\\특화프로젝트 DataSet\\추정매출\\clustering_dataset.json";
    static final String PATH2 = "C:\\Users\\multicampus\\Desktop\\특화프로젝트 DataSet\\추정매출\\people.json";

    public Map<String, Long> getCount(List<String> wordList) {
        JavaRDD<String> words = sc.parallelize(wordList);
        Map<String, Long> wordCounts = words.countByValue();
        readCSV();
        //readHDFS();
        return wordCounts;
    }

    public void readCSV() {

//        JavaRDD<String> data = sc.textFile(PATH2);
//        System.out.println("#####################");
//        System.out.println(data);
//        System.out.println(data.count());
//        System.out.println(data.collect());
//        System.out.println(data.collect().stream().iterator().hasNext());

//        System.out.println("#####################");

//        Dataset<Row> dataFrame = sparkSession.createDataFrame(data.collect(), Data.class);
//        dataFrame.show();
        Dataset<Row> people = sparkSession.read().format("json").load(PATH);
        people.show();
//        people.createOrReplaceTempView("people");
//        Dataset<Row> namesDF = sparkSession.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19");
//        namesDF.show();
//
//        KMeans kmeans = new KMeans().setK(2).setSeed(1L);
//        KMeansModel model = kmeans.fit(namesDF);


//        Iterator iter = data.collect().stream().iterator();
//        while(iter.hasNext()) {
//             System.out.println(iter.next());
//        }

//        data.map(new Function<String, EstimatedSales>() {
//
//            @Override
//            public EstimatedSales call(String line) throws Exception {
//
//                String[] parts = line.split(",");
//                System.out.println(parts[0]);
//                return null;
//            }
//        });
    }

    public void commercialAreaRecommendation(RecommendDto recommendDto) {
    }

    public void readHDFS() {
        JavaRDD<String> data = sc.textFile("hdfs://54.180.65.171/user/hduser2/estimated_sales2.csv");
        System.out.println(data.count());
    }

}