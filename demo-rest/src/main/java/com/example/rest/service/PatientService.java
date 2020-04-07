package com.example.rest.service;

import com.example.rest.model.Patient;

import java.util.List;

public interface PatientService extends CustomEntityService<Patient> {
    void create(Patient patient);
    List<Patient> findBySnils(String snils);
}
