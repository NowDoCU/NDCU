//package com.hotsix.semochang;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * author: pinest94
// * since: 2021-03-25
// */
//
//@Configuration
//public class SparkConfig {
//
//    @Value("${spark.app.name}")
//    private String appName;
//    @Value("${spark.master}")
//    private String masterUri;
//
//    @Bean
//    public SparkConf conf() {
//        return new SparkConf().setAppName(appName).setMaster(masterUri);
////                .set("spark.hadoop.fs.default.name", "hdfs://54.180.65.171:8020").set("spark.hadoop.fs.defaultFS", "hdfs://54.180.65.171:8020")
////                .set("spark.hadoop.fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName())
////                .set("spark.hadoop.fs.hdfs.server", org.apache.hadoop.hdfs.server.namenode.NameNode.class.getName())
////                .set("spark.hadoop.conf", org.apache.hadoop.hdfs.HdfsConfiguration.class.getName());
//    }
//
//    @Bean
//    public JavaSparkContext sc() {
//        return new JavaSparkContext(conf());
//    }
//}
