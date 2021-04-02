package com.hotsix.semochang.service;

import com.hotsix.semochang.model.Founder;
import com.hotsix.semochang.model.LoginRequestDTO;
import org.springframework.http.ResponseEntity;

/**
 * author: pinest94
 * since: 2021-04-01
 */

public interface FounderService {

    /***
     * founder 생성
     */
    ResponseEntity<?> create(Founder founder);

    /***
     * founder 가져오기
     */
    ResponseEntity<?> read(Long id);

    /***
     * founder 수정하기
     */
    ResponseEntity<?> update(Founder founder);

    /***
     * founder 삭제하기
     */
    ResponseEntity<?> delete(Long id);

    /***
     * 로그인 인증
     */
    ResponseEntity<?> authenticate(LoginRequestDTO loginRequestDTO);
}
