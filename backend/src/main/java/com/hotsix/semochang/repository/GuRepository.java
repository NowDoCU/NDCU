package com.hotsix.semochang.repository;

import com.hotsix.semochang.model.Gu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuRepository extends MongoRepository<Gu, String> {


}
