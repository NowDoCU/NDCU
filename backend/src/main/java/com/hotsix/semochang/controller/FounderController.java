package com.hotsix.semochang.controller;

import com.hotsix.semochang.model.Founder;
import com.hotsix.semochang.service.FounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * author: pinest94
 * since: 2021-04-01
 */

@RestController
@RequestMapping("${api}/founder")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class FounderController {

    @Autowired
    FounderService founderService;

    // 1. founder 생성
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Founder founder) {
        return founderService.create(founder);
    }

    // 2. founder 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable("id") Long id) {
        return founderService.read(id);
    }

    // 3. founder 수정
    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody Founder founder) {
        return founderService.update(founder);
    }

    // 4. founder 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return founderService.delete(id);
    }
}
