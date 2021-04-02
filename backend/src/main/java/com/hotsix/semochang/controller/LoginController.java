package com.hotsix.semochang.controller;

import com.hotsix.semochang.model.LoginRequestDTO;
import com.hotsix.semochang.model.LoginResponseDTO;
import com.hotsix.semochang.service.FounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * author: pinest94
 * since: 2021-04-01
 */

@RestController
@RequestMapping("${api}/login")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class LoginController {

    @Autowired
    FounderService founderService;

    @PostMapping("")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return founderService.authenticate(loginRequestDTO);
    }
}
