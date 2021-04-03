package com.hotsix.semochang.controller;

import com.hotsix.semochang.model.Commercial;
import com.hotsix.semochang.service.CommercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: pinest94
 * since: 2021-03-29
 */

@RestController
@RequestMapping("${api}/commercial")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class CommercialController {

    @Autowired
    CommercialService commercialService;

    @GetMapping("/{commercialCode}")
    public ResponseEntity<?> getCommercialInfo(@PathVariable("commercialCode") String commercialCode) {

        Commercial commercial = commercialService.findByCommercialCode(commercialCode);

        return new ResponseEntity<>(commercial, HttpStatus.OK);
    }

    @GetMapping("/list/{dongCode}")
    public ResponseEntity<?> commercialList(@PathVariable("dongCode") String dongCode) {

        List<Commercial> commercialList = commercialService.findByDongCode(dongCode);

        return new ResponseEntity<>(commercialList, HttpStatus.OK);
    }
}
