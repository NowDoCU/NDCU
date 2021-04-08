package com.hotsix.semochang.repository;

import com.hotsix.semochang.model.Commercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommercialRepository extends JpaRepository<Commercial, Long> {

    /***
     * 행정동 코드를 이용한 상권조회
     * @param dongCode
     * @return
     */
    List<Commercial> findByDongCode(String dongCode);

    /***
     * 상권코드를 이용한 상권조회
     * @param commercialCode
     * @return
     */
    Optional<Commercial> findByCommercialCode(String commercialCode);
}
