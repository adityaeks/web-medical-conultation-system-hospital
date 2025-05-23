package com.medical.web.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "consultations")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String disease;
    private String symptoms;
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id") // ini sudah benar, mengacu ke kolom id pada tabel doctors
    private Doctor doctor;

    public Consultation() { }

    public Consultation(String disease, String symptoms, LocalDate date) {
        this.disease = disease;
        this.symptoms = symptoms;
        this.date = date;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
}
