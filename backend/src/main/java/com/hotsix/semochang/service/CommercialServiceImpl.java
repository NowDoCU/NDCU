package com.hotsix.semochang.service;

import com.hotsix.semochang.model.EstimatedPopulation;
import com.hotsix.semochang.model.EstimatedSales;
import com.hotsix.semochang.repository.PopulationRepository;
import com.hotsix.semochang.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: pinest94
 * since: 2021-03-29
 */

@Service
public class CommercialServiceImpl implements CommercialService{

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    PopulationRepository populationRepository;

    @Override
    public List<EstimatedSales> findSalesByCommercialCode(String commercialCode) {
        return salesRepository.findByCommercialCodeOrderByIndustryCodeAscYearAscQuarterAsc(commercialCode);
    }

    @Override
    public List<EstimatedPopulation> findPopulationByCommercialCode(String commercialCode) {
        return populationRepository.findByCommercialCodeOrderByYearAscQuarterAsc(commercialCode);
    }
}
