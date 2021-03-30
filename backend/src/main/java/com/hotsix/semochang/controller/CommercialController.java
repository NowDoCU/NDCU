package com.hotsix.semochang.controller;

import com.hotsix.semochang.model.Commercial;
import com.hotsix.semochang.model.EstimatedPopulation;
import com.hotsix.semochang.model.EstimatedSales;
import com.hotsix.semochang.service.CommercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author: pinest94
 * since: 2021-03-29
 */

@RestController
@RequestMapping("${api}/commercial")
public class CommercialController {

    @Autowired
    CommercialService commercialService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommercialInfo(@PathVariable("id") Long id) {

        Commercial commercial = commercialService.findCommercialById(id);

        return new ResponseEntity<>(commercial, HttpStatus.OK);
    }

    @GetMapping("/list/{dongCode}")
    public ResponseEntity<?> commercialList(@PathVariable("dongCode") String dongCode) {
        List<Commercial> commercialList = commercialService.findCommercialByDongCode(dongCode);
        return new ResponseEntity<>(commercialList, HttpStatus.OK);
    }
}
