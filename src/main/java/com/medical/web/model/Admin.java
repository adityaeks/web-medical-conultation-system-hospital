package com.medical.web.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gmail;
    private String password;

    public Admin() { }

    public Admin(String name, String gmail, String password) {
        this.name = name;
        this.gmail = gmail;
        this.password = password;
    }

    // getters & setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGmail() {
        return gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
