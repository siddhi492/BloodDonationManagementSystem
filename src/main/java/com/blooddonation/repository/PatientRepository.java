package com.blooddonation.repository;

import com.blooddonation.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient, String> {
    Patient findByUsername(String username);
}
