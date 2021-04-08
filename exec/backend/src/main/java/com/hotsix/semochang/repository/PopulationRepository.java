package com.hotsix.semochang.repository;

import com.hotsix.semochang.model.EstimatedPopulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopulationRepository extends JpaRepository<EstimatedPopulation, Long> {

//    /***
//     * 상권코드를 이용한 유동인구 변화 조회
//     */
//    List<EstimatedPopulation> findByCommercialCodeOrderByYearAscQuarterAsc(String code);
}
