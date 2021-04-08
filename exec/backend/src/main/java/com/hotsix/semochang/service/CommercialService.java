package com.hotsix.semochang.service;

import com.hotsix.semochang.model.network.response.CommercialApiResponse;
import com.hotsix.semochang.model.network.response.CommercialListApiResponse;
import org.springframework.http.ResponseEntity;

public interface CommercialService {

    /***
     * 행정동코드를 이용한 상권조회
     * @param dongCode
     * @return
     */
    ResponseEntity<CommercialListApiResponse> findByDongCode(String dongCode);

    /***
     * 상권 코드를 이용한 상권조회
     * @param commercialCode
     * @return
     */
    ResponseEntity<CommercialApiResponse> findByCommercialCode(String commercialCode);
}
