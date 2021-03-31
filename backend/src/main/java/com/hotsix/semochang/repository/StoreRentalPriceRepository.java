package com.hotsix.semochang.repository;

import com.hotsix.semochang.model.StoreRentalPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRentalPriceRepository extends JpaRepository<StoreRentalPrice, String> {

}
