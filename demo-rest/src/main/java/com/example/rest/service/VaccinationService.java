package com.example.rest.service;

import com.example.rest.model.Patient;
import com.example.rest.model.Vaccination;

import java.util.List;

public interface VaccinationService extends CustomEntityService<Vaccination> {
    boolean create(Vaccination vaccination, String snils);
    List<Vaccination> findByPatientSnils(String snils);
}
