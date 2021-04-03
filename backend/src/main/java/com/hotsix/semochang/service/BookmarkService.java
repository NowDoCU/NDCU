package com.hotsix.semochang.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface BookmarkService {

    /***
     * 북마크 추가
     */
    ResponseEntity<?> create(String commercialCode, Authentication authentication);

    /***
     * 북마크 삭제
     */
    ResponseEntity<?> delete(Long id);
}
