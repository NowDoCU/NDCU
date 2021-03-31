package com.hotsix.semochang.service;

import com.hotsix.semochang.model.Commercial;
import com.hotsix.semochang.model.EstimatedPopulation;
import com.hotsix.semochang.model.EstimatedSales;

import java.util.List;

public interface CommercialService {

    /***
     * 행정동코드를 이용한 상권조회
     * @param dongCode
     * @return List<Commercial>
     */
    List<Commercial> findByDongCode(String dongCode);

    /***
     * 상권 코드를 이용한 상권조회
     * @param commercialCode
     * @return
     */
    Commercial findByCommercialCode(String commercialCode);
}
