package com.hotsix.semochang.repository;

import com.hotsix.semochang.model.Commercial;
import com.hotsix.semochang.model.EstimatedPopulation;
import com.hotsix.semochang.model.EstimatedSales;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SalesRepositoryTest {

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    CommercialRepository commercialRepository;

    @Test
    @DisplayName("commercial 외부키 연동")
    @Disabled("외부키 생성 완료되었으므로 테스트 제외")
    public void makeForeignKey() {
        List<EstimatedSales> estimatedSalesList
                = salesRepository.findAll();

//        for (EstimatedSales estimatedSales : estimatedSalesList) {
//            Commercial commercial = commercialRepository.findByCommercialCode(estimatedSales.getCommercialCode()).orElse(null);
//
//            if(commercial != null) {
//                Long commercialId = commercial.getId();
//                estimatedSales.setCommercialId(commercialId);
//                salesRepository.save(estimatedSales);
//            }
//        }
    }
}