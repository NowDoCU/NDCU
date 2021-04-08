//package com.hotsix.semochang.controller;
//
//import com.hotsix.semochang.service.RecommendService;
//import com.hotsix.semochang.model.RecommendDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class RecommendController {
//
//    @Autowired
//    RecommendService service;
//
//    @GetMapping("/wordcount/{words}")
//    public Map<String, Long> count(@PathVariable String words) {
//        System.out.println(words);
//        List<String> wordList = Arrays.asList(words.split(" "));
//        return service.getCount(wordList);
//    }
//
//    @PostMapping("/recommendation")
//    public void recommendation(@RequestBody RecommendDTO recommendDto) {
//        service.commercialAreaRecommend(recommendDto);
//    }
//}
