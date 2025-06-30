package com.blooddonation.repository;

import com.blooddonation.model.Donor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DonorRepository extends MongoRepository<Donor, String> {
    Donor findByUsername(String username);
}
