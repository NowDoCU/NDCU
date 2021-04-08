package com.hotsix.semochang.controller;

import com.hotsix.semochang.model.Gu;
import com.hotsix.semochang.repository.GuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author: pinest94
 * since: 2021-04-07
 */

@RestController
@RequestMapping("${api}/gu")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class GuController {

    @Autowired
    GuRepository guRepository;

    @GetMapping("")
    public ResponseEntity<?> list() {
        List<Gu> guList = guRepository.findAll();
        return new ResponseEntity<>(guList, HttpStatus.OK);
    }
}
