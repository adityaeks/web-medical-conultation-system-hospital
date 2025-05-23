package com.medical.web.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gmail;
    private String password;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations = new ArrayList<>();

    public Patient() { }

    public Patient(String name, String gmail, String password) {
        this.name = name;
        this.gmail = gmail;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGmail() { return gmail; }
    public void setGmail(String gmail) { this.gmail = gmail; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public List<Consultation> getConsultations() { return consultations; }

    public void addConsultation(Consultation consultation) {
        if (consultation != null) {
            consultations.add(consultation);
            consultation.setPatient(this);
        }
    }

    public void removeConsultation(Consultation consultation) {
        if (consultation != null && consultations.remove(consultation)) {
            consultation.setPatient(null);
        }
    }
}