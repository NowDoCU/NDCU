package com.hotsix.semochang.repository;

import com.hotsix.semochang.model.EstimatedSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<EstimatedSales, Long> {

//    /***
//     * 상권코드를 이용한 매출변화 조회
//     */
//    List<EstimatedSales> findByCommercialCodeOrderByIndustryCodeAscYearAscQuarterAsc(String code);
}
