//package com.hotsix.semochang.service;
//
//import com.hotsix.semochang.model.RecommendDTO;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//
//import org.apache.spark.ml.clustering.BisectingKMeans;
//import org.apache.spark.ml.clustering.BisectingKMeansModel;
//import org.apache.spark.ml.clustering.KMeansModel;
//import org.apache.spark.ml.clustering.KMeans;
//import org.apache.spark.ml.evaluation.ClusteringEvaluator;
//import org.apache.spark.ml.linalg.Vector;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.spark.sql.*;
//
//@Service
//public class RecommendService {
//
//    @Autowired
//    JavaSparkContext sc;
//
//    @Autowired
//    SparkSession sparkSession;
//
//    static final String PATH_json = "C:\\Users\\multicampus\\Desktop\\특화프로젝트 DataSet\\추정매출\\clustering_dataset.json";
//    static final String PATH_csv = "C:\\Users\\multicampus\\Desktop\\특화프로젝트 DataSet\\추정매출\\clustering_dataset.csv";
//    static final String PATH2 = "C:\\Users\\multicampus\\Desktop\\특화프로젝트 DataSet\\추정매출\\people.json";
//    static final String PATH_svm = "C:\\Users\\multicampus\\Desktop\\특화프로젝트 DataSet\\추정매출\\sample_kmeans_data.txt";
//    static final String PATH_col = "C:\\Users\\multicampus\\Desktop\\특화프로젝트 DataSet\\추정매출\\clustering_dataset_col_y.csv";
//
//    public Map<String, Long> getCount(List<String> wordList) {
//        JavaRDD<String> words = sc.parallelize(wordList);
//        Map<String, Long> wordCounts = words.countByValue();
//        readCSV();
//        //readHDFS();
//        return wordCounts;
//    }
//
//    public Dataset<Row> readCSV() {
//
////        Dataset<Row> dataFrame = sparkSession.createDataFrame(data.collect(), Data.class);
////        dataFrame.show();
////        Dataset<Row> people = sparkSession.read().format("csv").load(PATH_csv);
////        Dataset<Row> cluster_col = sparkSession.read().format("csv").load(PATH_col);
////        people.show();
//
//        Dataset<Row> people = sparkSession.read().format("libsvm").load(PATH_svm);
////        people.show();
////        people.createOrReplaceTempView("people");
////        Dataset<Row> namesDF = sparkSession.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19");
////        namesDF.show();
//
//        // Trains a k-means model.
//        KMeans kmeans = new KMeans().setK(2).setSeed(1L);
//        KMeansModel model = kmeans.fit(people);
//
//        // Make predictions
//        Dataset<Row> predictions = model.transform(people);
//
//        // Evaluate clustering by computing Silhouette score
//        ClusteringEvaluator evaluator = new ClusteringEvaluator();
//
//        double silhouette = evaluator.evaluate(predictions);
//        System.out.println("Silhouette with squared euclidean distance = " + silhouette);
//
//        // Shows the result.
//        Vector[] centers = model.clusterCenters();
//        System.out.println("Cluster Centers: ");
//        for (Vector center: centers) {
//            System.out.println(center);
//        }
//
//        return people;
//    }
//
//    public void commercialAreaRecommend(RecommendDTO recommendDto) {
//    }
//
//    public void readHDFS() {
//        JavaRDD<String> data = sc.textFile("hdfs://54.180.65.171/user/hduser2/estimated_sales2.csv");
//        System.out.println(data.count());
//    }
//
//}