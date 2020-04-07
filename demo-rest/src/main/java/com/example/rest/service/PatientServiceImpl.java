package com.example.rest.service;

import com.example.rest.repository.PatientRepository;
import com.example.rest.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Patient patient) {
        repository.save(patient);
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> result = new ArrayList<>();
        repository.findAll()
                .forEach(result::add);
        return result;
    }

    @Override
    public Patient findById(Long id) {
        return repository.findById(id)
                    .orElse(null);
    }

    public List<Patient> findBySnils(String snils) {
        return repository.findBySnils(snils);
    }

    @Override
    public boolean update(Patient patient, Long id) {
        if (repository.existsById(id)) {
            patient.setId(id);
            repository.save(patient);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}