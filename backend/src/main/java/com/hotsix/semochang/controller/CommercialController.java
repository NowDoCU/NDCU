package com.hotsix.semochang.controller;

import com.hotsix.semochang.model.Commercial;
import com.hotsix.semochang.model.network.response.CommercialApiResponse;
import com.hotsix.semochang.model.network.response.CommercialListApiResponse;
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
    public ResponseEntity<CommercialApiResponse> getCommercialInfo(@PathVariable("commercialCode") String commercialCode) {
        return commercialService.findByCommercialCode(commercialCode);
    }

    @GetMapping("/list/{dongCode}")
    public ResponseEntity<CommercialListApiResponse> commercialList(@PathVariable("dongCode") String dongCode) {

        return commercialService.findByDongCode(dongCode);
    }
}
