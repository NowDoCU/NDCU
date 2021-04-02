package com.hotsix.semochang.controller;

import com.hotsix.semochang.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.hotsix.semochang.utils.ExceptionUtil.isInValidAuthentication;

/**
 * author: pinest94
 * since: 2021-04-02
 */

@RestController
@RequestMapping("${api}/bookmark")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class BookmarkController {

    @Autowired
    BookmarkService bookmarkService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Map<String, String> request,
                                    Authentication authentication) {
        // 유효하지 않은 토큰인 경우
        if (isInValidAuthentication(authentication)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String commercialCode = request.get("commercialCode");
        return bookmarkService.create(commercialCode, authentication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return bookmarkService.delete(id);
    }
}