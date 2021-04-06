package com.hotsix.semochang.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface BookmarkService {

    /***
     * 북마크 생성
     * @param commercialCode
     * @param authentication
     * @return
     */
    ResponseEntity<?> create(String commercialCode, Authentication authentication);

    /***
     * 북마크 삭제
     * @param id
     * @return
     */
    ResponseEntity<?> delete(Long id);

    /***
     * 북마크 리스트 생성
     * @param authentication
     * @return
     */
    ResponseEntity<?> read(Authentication authentication);
}
