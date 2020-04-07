package com.example.rest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Data
@Entity
public class Vaccination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @NotNull(message = "Препарат не может быть пустым")
    @Size(max = 140)
    private String preparation;
    @NotNull(message = "Согласие не может быть пустым")
    private boolean agreement;
    @NotNull(message = "Серия не может быть пустой")
    @Size(max = 40)
    private String series;
    @NotNull(message = "Количество не может быть пустым")
    @Min(1)
    private Integer count;
    @NotNull(message = "Дата прививки не может быть пустой")
    private java.sql.Date dateOf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patient patient;

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Vaccination() {}

    public Vaccination(String preparation,
                boolean agreement,
                String series,
                Integer count,
                java.sql.Date dateOf) {
        this.preparation = preparation;
        this.agreement = agreement;
        this.series = series;
        this.count = count;
        this.dateOf = dateOf;
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vaccination that = (Vaccination) o;
        return agreement == that.agreement &&
                id.equals(that.id) &&
                preparation.equals(that.preparation) &&
                series.equals(that.series) &&
                count.equals(that.count) &&
                dateOf.equals(that.dateOf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, preparation, agreement, series, count, dateOf);
    }

    @Override
    public String toString() {
        return "Vaccination{" +
                "id=" + id +
                ", preparation='" + preparation + '\'' +
                ", agreement=" + agreement +
                ", series='" + series + '\'' +
                ", count=" + count +
                ", dateOf=" + dateOf +
                //", patient=" + patient +
                '}';
    }
}
