package com.medical.web.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gmail;
    private String password;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultationsReceived = new ArrayList<>();

    public Doctor() { }

    public Doctor(String name, String gmail, String password) {
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
    public List<Consultation> getConsultationsReceived() { return consultationsReceived; }

    public void addConsultation(Consultation consultation) {
        if (consultation != null) {
            consultationsReceived.add(consultation);
            consultation.setDoctor(this);
        }
    }

    public void removeConsultation(Consultation consultation) {
        if (consultation != null && consultationsReceived.remove(consultation)) {
            consultation.setDoctor(null);
        }
    }
}
