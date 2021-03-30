package com.hotsix.semochang.controller;

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

    @GetMapping("/sales/{code}")
    public ResponseEntity<?> salesList(@PathVariable("code") String code) {

        List<EstimatedSales> salesList = commercialService.findSalesByCommercialCode(code);

        return new ResponseEntity<>(salesList, HttpStatus.OK);
    }

    @GetMapping("/population/{code}")
    public ResponseEntity<?> populationList(@PathVariable("code") String code) {

        List<EstimatedPopulation> populationList = commercialService.findPopulationByCommercialCode(code);

        return new ResponseEntity<>(populationList, HttpStatus.OK);
    }
}
