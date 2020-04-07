package com.example.rest.repository;

import com.example.rest.model.Vaccination;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VaccinationRepository extends CrudRepository<Vaccination, Long> {
    List<Vaccination> findByPatientSnils(String patientSnils);
}
