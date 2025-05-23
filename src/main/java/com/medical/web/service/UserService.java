package com.medical.web.service;

import com.medical.web.model.Consultation;
import com.medical.web.model.Doctor;
import com.medical.web.model.Patient;
import com.medical.web.repo.AdminRepository;
import com.medical.web.repo.ConsultationRepository;
import com.medical.web.repo.DoctorRepository;
import com.medical.web.repo.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired private AdminRepository adminRepo;
    @Autowired private PatientRepository patientRepo;
    @Autowired private DoctorRepository doctorRepo;
    @Autowired private ConsultationRepository consultRepo;

    // ===== ADMIN =====
    public boolean adminLogin(String name, String password) {
        return adminRepo.findByName(name)
                        .filter(a -> a.getPassword().equals(password))
                        .isPresent();
    }

    // ===== PATIENT =====
    public boolean patientLogin(String name, String password) {
        return patientRepo.findByName(name)
                          .filter(p -> p.getPassword().equals(password))
                          .isPresent();
    }

    public Patient registerPatient(String name, String gmail, String password) {
        Patient p = new Patient(name, gmail, password);
        return patientRepo.save(p);
    }

    public boolean resetPatientPassword(String name, String gmail, String newPassword) {
        Optional<Patient> opt = patientRepo.findByName(name);
        if (opt.isPresent() && opt.get().getGmail().equalsIgnoreCase(gmail)) {
            Patient p = opt.get();
            p.setPassword(newPassword);
            patientRepo.save(p);
            return true;
        }
        return false;
    }

    // ===== DOCTOR =====
    public boolean doctorLogin(String name, String password) {
        return doctorRepo.findByName(name)
                         .filter(d -> d.getPassword().equals(password))
                         .isPresent();
    }

    public Long getDoctorIdByName(String name) {
        var doctorOpt = doctorRepo.findByNameIgnoreCase(name.trim());
        if (doctorOpt.isPresent()) {
            System.out.println("Doctor found: " + doctorOpt.get().getName() + ", id=" + doctorOpt.get().getId());
            return doctorOpt.get().getId();
        } else {
            System.out.println("Doctor NOT found for name: [" + name + "]");
            return null;
        }
    }

    public Doctor registerDoctor(String name, String gmail, String password) {
        Doctor d = new Doctor(name, gmail, password);
        return doctorRepo.save(d);
    }

    public boolean resetDoctorPassword(String name, String gmail, String newPassword) {
        Optional<Doctor> opt = doctorRepo.findByName(name);
        if (opt.isPresent() && opt.get().getGmail().equalsIgnoreCase(gmail)) {
            Doctor d = opt.get();
            d.setPassword(newPassword);
            doctorRepo.save(d);
            return true;
        }
        return false;
    }

    // ===== CONSULTATION =====
    public Consultation createConsultation(Long patientId, Long doctorId,
                                           String disease, String symptoms, LocalDate date) {
        Patient p = patientRepo.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor d = doctorRepo.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        Consultation c = new Consultation(disease, symptoms, date);
        c.setPatient(p);
        c.setDoctor(d);
        return consultRepo.save(c);
    }

    // ===== FETCH CONSULTATIONS =====
    public Doctor getDoctorWithConsultations(Long doctorId) {
        return doctorRepo.findById(doctorId)
                         .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    // ===== STATISTICS =====
    public long getTotalDoctors() {
        return doctorRepo.count();
    }

    public long getTotalPatients() {
        return patientRepo.count();
    }

    public long getTotalConsultations() {
        return consultRepo.count();
    }

    public long getDoctorScheduleCount(Long doctorId) {
        return consultRepo.countByDoctorId(doctorId);
    }

    public List<Doctor> getAllDoctors() {
        var doctors = doctorRepo.findAll();
        System.out.println("Mengambil data dokter dari database...");
        System.out.println("Jumlah dokter: " + doctors.size());
        for (var doctor : doctors) {
            System.out.println("Dokter ditemukan: ID=" + doctor.getId() + ", Nama=" + doctor.getName());
        }
        return doctors;
    }

    public Long getPatientIdByName(String name) {
        var patientOpt = patientRepo.findByName(name.trim());
        if (patientOpt.isPresent()) {
            System.out.println("Patient found: " + patientOpt.get().getName() + ", id=" + patientOpt.get().getId());
            return patientOpt.get().getId();
        } else {
            System.out.println("Patient NOT found for name: [" + name + "]");
            return null;
        }
    }
}