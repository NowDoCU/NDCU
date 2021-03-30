package com.hotsix.semochang.service;

import com.hotsix.semochang.model.Commercial;
import com.hotsix.semochang.model.EstimatedPopulation;
import com.hotsix.semochang.model.EstimatedSales;

import java.util.List;

public interface CommercialService {

    /***
     * 상권코드를 이용한 매출변화 추이
     * @param commercialCode
     * @return List<EstimatedSales>
     */
    List<EstimatedSales> findSalesByCommercialCode(String commercialCode);

    /***
     * 상권코드를 이용한 매출변화 추이
     * @param commercialCode
     * @return List<EstimatedPopulation>
     */
    List<EstimatedPopulation> findPopulationByCommercialCode(String commercialCode);

    /***
     * 행정동코드를 이용한 상권조회
     * @param dongCode
     * @return List<Commercial>
     */
    List<Commercial> findCommercialByDongCode(String dongCode);

    /***
     * 상권 id를 이용한 상권조회
     * @param id
     * @return
     */
    Commercial findCommercialById(Long id);
}
