package com.hotsix.semochang.repository;

import com.hotsix.semochang.model.Commercial;
import com.hotsix.semochang.model.EstimatedPopulation;
import com.hotsix.semochang.model.EstimatedSales;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommercialRepositoryTest {

    @Autowired
    CommercialRepository commercialRepository;

    @Test
    @DisplayName("행정동 코드를 통한 상권조회")
    public void listTest() {

        /***
         * 1. 행정동 코드를 통한 상권조회
         */
        String dongCode = "11440660";

        List<Commercial> commercialList = commercialRepository.findByDongCode(dongCode);

        commercialList.forEach(System.out::println);
    }

    @Test
    @DisplayName("하나의 상권에서 추정매출흐름 조회")
    @Transactional
    public void findByIdAndSalesListTest() {
        
        /***
         * 2. 하나의 상권에서 추정매출흐름 조회
         */

        Long commercialId = 1157L;

        Commercial commercial = commercialRepository.findById(commercialId).orElse(null);

        // 상권 정보 조회
        System.out.println(commercial);

        // 상권이 가지고 있는 매출추이 조회
        commercial.getEstimatedSalesList().stream()
                .sorted(Comparator.comparing(EstimatedSales::getQuarter))
                .sorted(Comparator.comparing(EstimatedSales::getYear))
                .sorted(Comparator.comparing(EstimatedSales::getIndustryCode)).forEach(System.out::println);
    }

    @Test
    @DisplayName("하나의 상권에서 추정유동인구 조회")
    @Transactional
    public void findByIdAndPopulationListTest() {

        /***
         * 2. 하나의 상권에서 추정매출흐름 조회
         */
        Long commercialId = 1157L;

        Commercial commercial = commercialRepository.findById(commercialId).orElse(null);

        // 상권 정보 조회
        System.out.println(commercial);

        // 상권이 가지고 있는 매출추이 조회
        commercial.getEstimatedPopulationList().stream()
                .sorted(Comparator.comparing(EstimatedPopulation::getQuarter))
                .sorted(Comparator.comparing(EstimatedPopulation::getYear)).forEach(System.out::println);
    }
}