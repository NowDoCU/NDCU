package com.hotsix.semochang.repository;

import com.hotsix.semochang.model.Commercial;
import com.hotsix.semochang.model.EstimatedPopulation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PopulationRepositoryTest {

    @Autowired
    PopulationRepository populationRepository;

    @Autowired
    CommercialRepository commercialRepository;

    @Test
    public void readTest() {
        Long commercialId = 1476L;

        List<EstimatedPopulation> estimatedPopulationList
                = populationRepository.findByCommercialId(commercialId);

        estimatedPopulationList.forEach(System.out::println);
    }

    @Test
    @DisplayName("commercial 외부키 연동")
    @Disabled("외부키 생성 완료되었으므로 테스트 제외")
    public void makeForeignKey() {
        List<EstimatedPopulation> estimatedPopulationList
                = populationRepository.findAll();

//        for (EstimatedPopulation estimatedPopulation : estimatedPopulationList) {
//            Commercial commercial = commercialRepository.findByCommercialCode(estimatedPopulation.getCommercialCode()).orElse(null);
//
//            if(commercial != null) {
//                Long commercialId = commercial.getId();
//                // estimatedPopulation.setCommercialId(commercialId);
//                populationRepository.save(estimatedPopulation);
//            }
//        }
    }

}