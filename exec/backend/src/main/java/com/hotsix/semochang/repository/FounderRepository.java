package com.hotsix.semochang.repository;

import com.hotsix.semochang.model.Founder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FounderRepository extends JpaRepository<Founder, Long> {

    /***
     * 이메일을 통해 founder 조회
     * @param email
     * @return
     */
    Optional<Founder> findByEmail(String email);
}
