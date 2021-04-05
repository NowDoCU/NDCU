package com.hotsix.semochang.controller;

import com.hotsix.semochang.model.Founder;
import com.hotsix.semochang.model.network.request.FounderApiRequest;
import com.hotsix.semochang.model.network.response.FounderApiResponse;
import com.hotsix.semochang.service.FounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<?> create(@RequestBody FounderApiRequest request) {
        return founderService.create(request);
    }

    // 2. founder 가져오기
    @PostMapping("/info")
    public ResponseEntity<FounderApiResponse> read(Authentication authentication) {
        return founderService.read(authentication);
    }

    // 3. founder 수정
    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody FounderApiRequest request, Authentication authentication) {
        return founderService.update(request, authentication);
    }

    // 4. founder 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return founderService.delete(id);
    }
}
