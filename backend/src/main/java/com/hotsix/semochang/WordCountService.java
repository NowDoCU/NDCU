package com.hotsix.semochang;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * author: pinest94
 * since: 2021-03-25
 */
@Service
public class WordCountService {

    @Autowired
    JavaSparkContext sc;

    static final String PATH = "C:\\Users\\multicampus\\Desktop\\특화프로젝트 DataSet\\추정매출\\estimated_sales2.csv";

    public Map<String, Long> getCount(List<String> wordList) {
        JavaRDD<String> words = sc.parallelize(wordList);
        Map<String, Long> wordCounts = words.countByValue();
        readCSV();
        return wordCounts;
    }

    public void readCSV() {
        JavaRDD<String> data = sc.textFile(PATH);
        System.out.println(data);
        System.out.println(data.count());
        System.out.println(data.collect().stream().iterator().hasNext());

        Iterator iter = data.collect().stream().iterator();
//        while(iter.hasNext()) {
//            // System.out.println(iter.next());
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

}