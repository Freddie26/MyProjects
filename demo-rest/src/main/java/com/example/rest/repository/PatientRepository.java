package com.example.rest.repository;

import com.example.rest.model.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Long> {
    List<Patient> findBySnils(String snils);
}
