package com.example.rest.controller;

import com.example.rest.model.Vaccination;
import com.example.rest.service.VaccinationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/vaccinations")
@Validated
public class VaccinationController {

    private VaccinationService service;

    VaccinationController(VaccinationService repository) {
        this.service = repository;
    }

    @PostMapping("/snils/{snils}")
    public ResponseEntity<?> create(@RequestBody @Valid Vaccination vaccination,
                                    @PathVariable @Pattern(regexp = "^[0-9]{11}$") String snils) {
        final boolean created = service.create(vaccination, snils);

        return (created)
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<Vaccination>> readAll() {
        final List<Vaccination> vaccinations = service.findAll();

        return (vaccinations == null || vaccinations.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(vaccinations, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Vaccination> read(@PathVariable @NotNull Long id) {
        final Vaccination vaccination = service.findById(id);

        return (vaccination == null)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(vaccination, HttpStatus.OK);
    }

    @GetMapping("/snils/{snils}")
    public ResponseEntity<List<Vaccination>> findByPatientSnils(@PathVariable @Pattern(regexp = "^[0-9]{11}$") String snils) {
        final List<Vaccination> vaccinations = service.findByPatientSnils(snils);

        return (vaccinations == null || vaccinations.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(vaccinations, HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid Vaccination vaccination, @PathVariable @NotNull Long id) {
        final boolean updated = service.update(vaccination, id);

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
