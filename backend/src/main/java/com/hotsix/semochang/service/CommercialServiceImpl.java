package com.hotsix.semochang.service;

import com.hotsix.semochang.model.Commercial;
import com.hotsix.semochang.model.EstimatedPopulation;
import com.hotsix.semochang.model.EstimatedSales;
import com.hotsix.semochang.repository.CommercialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author: pinest94
 * since: 2021-03-29
 */

@Service
public class CommercialServiceImpl implements CommercialService{

    @Autowired
    CommercialRepository commercialRepository;

    @Override
    @Transactional
    public List<Commercial> findByDongCode(String dongCode) {
        List<Commercial> commercialList = commercialRepository.findByDongCode(dongCode);

        // 상권리스트에 세부정보는 전달할 필요가 없기 때문에 제외시켜준다.
        for(Commercial commercial : commercialList) {
            commercial.setEstimatedPopulationList(null);
            commercial.setEstimatedSalesList(null);
        }

        return commercialList;
    }

    @Override
    @Transactional
    public Commercial findByCommercialCode(String commercialCode) {
        Commercial commercial = commercialRepository.findByCommercialCode(commercialCode).orElse(null);

        // 상권리스트에 세부정보는 전달할 필요가 없기 때문에 제외시켜준다.
        commercial.setEstimatedPopulationList(
                commercial.getEstimatedPopulationList()
                        .stream()
                        .sorted(Comparator.comparing(EstimatedPopulation::getQuarter))
                        .sorted(Comparator.comparing(EstimatedPopulation::getYear))
                        .collect(Collectors.toList())
        );

        commercial.setEstimatedSalesList(
                commercial.getEstimatedSalesList()
                        .stream()
                        .sorted(Comparator.comparing(EstimatedSales::getQuarter))
                        .sorted(Comparator.comparing(EstimatedSales::getYear))
                        .sorted(Comparator.comparing(EstimatedSales::getIndustryCode))
                        .collect(Collectors.toList())
        );

        return commercial;
    }
}
