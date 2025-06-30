package com.blooddonation.repository;

import com.blooddonation.model.BloodRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BloodRequestRepository extends MongoRepository<BloodRequest, String> {
    BloodRequest findByUsername(String username);
}
