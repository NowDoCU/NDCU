package com.hotsix.semochang;

import com.hotsix.semochang.model.RecommendDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * author: pinest94
 * since: 2021-03-25
 */
@RestController
public class WordCountController {

    @Autowired
    WordCountService service;

    @GetMapping("/wordcount/{words}")
    public Map<String, Long> count(@PathVariable String words) {
        System.out.println(words);
        List<String> wordList = Arrays.asList(words.split(" "));
        return service.getCount(wordList);
    }

    @PostMapping("/recommendation")
    public void recommendation(@RequestBody RecommendDto recommendDto) {
        service.commercialAreaRecommendation(recommendDto);
    }
}
