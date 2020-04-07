package com.example.rest.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @NotNull
    @Size(min = 2, max = 64)
    private String surname;
    @NotNull
    @Size(min = 2, max = 64)
    private String name;
    @Size(min = 0, max = 64)
    private String lastName;
    @NotNull(message = "Дата рождения не может быть пустой")
//    @PastOrPresent(message = "Дата рождения должна быть меньше либо равна 'сегодня'")
    private java.sql.Date birthday;
    @NotNull(message = "СНИЛС не может быть пустым")
    @Pattern(regexp = "^[0-9]{11}$", message = "СНИЛС не соответствует маске [^[0-9]{11}$]")
    @Size(min = 11, max = 11, message = "Длина СНИЛС должна быть 11 символов")
    private String snils;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Vaccination> vaccinations = new HashSet<>();

    public Set<Vaccination> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(Set<Vaccination> vaccinations) {
        this.vaccinations = vaccinations;
    }

    public boolean addVaccination(Vaccination vaccination) {
        vaccination.setPatient(this);
        return getVaccinations().add(vaccination);
    }

    public void removeVaccination(Vaccination vaccination) {
        getVaccinations().remove(vaccination);
    }

    public Patient() {}

    public Patient(String surname,
            String name,
            String lastName,
            java.sql.Date birthday,
            String snils) {
        this.surname = surname;
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.snils = snils;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id.equals(patient.id) &&
                surname.equals(patient.surname) &&
                name.equals(patient.name) &&
                Objects.equals(lastName, patient.lastName) &&
                birthday.equals(patient.birthday) &&
                snils.equals(patient.snils);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, lastName, birthday, snils);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", snils='" + snils + '\'' +
                ", vaccinations=" + vaccinations +
                '}';
    }
}
