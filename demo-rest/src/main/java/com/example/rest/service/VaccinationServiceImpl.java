package com.example.rest.service;

import com.example.rest.model.Patient;
import com.example.rest.model.Vaccination;
import com.example.rest.repository.VaccinationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VaccinationServiceImpl implements VaccinationService {

    private final VaccinationRepository repository;
    private final PatientService patientService;

    public VaccinationServiceImpl(VaccinationRepository repository, PatientService patientService) {
        this.repository = repository;
        this.patientService = patientService;
    }

    @Override
    public boolean create(Vaccination vaccination, String snils) {
        List<Patient> patients = patientService.findBySnils(snils);
        if (patients == null || patients.size() != 1) {
            return false;
        } else {
            vaccination.setPatient(patients.get(0));
            repository.save(vaccination);
            return true;
        }
    }

    @Override
    public List<Vaccination> findAll() {
        List<Vaccination> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Vaccination findById(Long id) {
        return repository.findById(id)
                .orElse(null);
    }

    @Override
    public List<Vaccination> findByPatientSnils(String snils) {
        return repository.findByPatientSnils(snils);
    }

    @Override
    public boolean update(Vaccination vaccination, Long id) {
        if (repository.existsById(id)) {
            vaccination.setId(id);
            repository.save(vaccination);
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
