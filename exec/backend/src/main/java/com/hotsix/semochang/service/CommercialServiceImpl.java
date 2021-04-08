package com.hotsix.semochang.service;

import com.hotsix.semochang.model.Commercial;
import com.hotsix.semochang.model.network.response.CommercialApiResponse;
import com.hotsix.semochang.model.network.response.CommercialListApiResponse;
import com.hotsix.semochang.repository.CommercialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<CommercialListApiResponse> findByDongCode(String dongCode) {
        List<Commercial> commercialList = commercialRepository.findByDongCode(dongCode);

        for(Commercial commercial : commercialList) {
            commercial.setEstimatedPopulationList(null);
            commercial.setEstimatedSalesList(null);
            commercial.setStoreRentalPrice(null);
            commercial.setFacilities(null);
        }

        CommercialListApiResponse response = CommercialListApiResponse.builder().commercialList(commercialList).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CommercialApiResponse> findByCommercialCode(String commercialCode) {
        Optional<Commercial> optional = commercialRepository.findByCommercialCode(commercialCode);

        return optional
                .map(commercial -> {
                    CommercialApiResponse response = CommercialApiResponse.builder()
                            .commercialCode(commercial.getCommercialCode())
                            .commercialName(commercial.getCommercialName())
                            .divisionCode(commercial.getDivisionCode())
                            .divisionName(commercial.getDivisionName())
                            .x(commercial.getX())
                            .y(commercial.getY())
                            .estimatedPopulationList(commercial.getEstimatedPopulationList())
                            .estimatedSalesList(commercial.getEstimatedSalesList()
                                    .stream()
                                    .filter(x -> x.getYear().equals("2020"))
                                    .filter(x -> x.getQuarter().equals("4"))
                                    .collect(Collectors.toList()))
                            .storeRentalPrice(commercial.getStoreRentalPrice())
                            .facilities(commercial.getFacilities())
                            .build();

                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
