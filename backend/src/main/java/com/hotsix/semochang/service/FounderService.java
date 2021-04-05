package com.hotsix.semochang.service;

import com.hotsix.semochang.model.Founder;
import com.hotsix.semochang.model.network.request.FounderApiRequest;
import com.hotsix.semochang.model.network.request.LoginApiRequest;
import com.hotsix.semochang.model.network.response.FounderApiResponse;
import com.hotsix.semochang.model.network.response.LoginApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

/**
 * author: pinest94
 * since: 2021-04-01
 */

public interface FounderService {

    /***
     * founder 생성
     */
    ResponseEntity<?> create(FounderApiRequest request);

    /***
     * founder 가져오기
     */
    ResponseEntity<FounderApiResponse> read(Authentication authentication);

    /***
     * founder 수정하기
     */
    ResponseEntity<?> update(FounderApiRequest request, Authentication authentication);

    /***
     * founder 삭제하기
     */
    ResponseEntity<?> delete(Long id);

    /***
     * 로그인 인증
     */
    ResponseEntity<?> authenticate(LoginApiRequest request);
}
