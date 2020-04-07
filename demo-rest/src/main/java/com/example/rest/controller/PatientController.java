package com.example.rest.controller;

import com.example.rest.model.Patient;
import com.example.rest.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
@RequestMapping("/patients")
class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Patient patient) {
        service.create(patient);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Patient>> readAll() {
        final List<Patient> patients = service.findAll();

        return (patients == null || patients.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Patient> read(@PathVariable @NotNull Long id) {
        final Patient patient = service.findById(id);

        return (patient == null)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @GetMapping("/snils/{snils}")
    public ResponseEntity<List<Patient>> findBySnils(@PathVariable @NotNull String snils) {
        final List<Patient> patients = service.findBySnils(snils);

        return (patients == null || patients.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid Patient patient, @PathVariable @NotNull Long id) {
        final boolean updated = service.update(patient, id);

        return (updated)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable @NotNull Long id) {
        final boolean deleted = service.delete(id);

        return (deleted)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
