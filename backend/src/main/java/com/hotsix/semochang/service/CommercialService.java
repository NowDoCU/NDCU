package com.hotsix.semochang.service;

import com.hotsix.semochang.model.EstimatedPopulation;
import com.hotsix.semochang.model.EstimatedSales;

import java.util.List;

public interface CommercialService {

    /***
     * 상권코드를 이용한 매출변화 추이
     */
    List<EstimatedSales> findSalesByCommercialCode(String commercialCode);

    /***
     * 상권코드를 이용한 매출변화 추이
     */
    List<EstimatedPopulation> findPopulationByCommercialCode(String commercialCode);
}
