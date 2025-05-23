// src/main/java/com/medical/web/controller/ConsultationController.java
package com.medical.web.controller;

import com.medical.web.model.Consultation;
import com.medical.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import jakarta.servlet.http.HttpSession;

@Controller
public class ConsultationController {

    @Autowired
    private UserService userService;

    @GetMapping("/consult")
    public String showConsultForm(Model model, HttpSession session) {
        // Ambil ID pasien dari session
        Object patientId = session.getAttribute("patientId");
        if (patientId == null) {
            model.addAttribute("message", "Anda harus login sebagai pasien untuk melakukan konsultasi.");
            return "consult-result";
        }
        model.addAttribute("patientId", patientId);
        
        // Ambil daftar dokter
        var doctors = userService.getAllDoctors();
        System.out.println("Jumlah dokter yang ditemukan: " + doctors.size());
        for (var doctor : doctors) {
            System.out.println("Dokter: ID=" + doctor.getId() + ", Nama=" + doctor.getName());
        }
        model.addAttribute("doctors", doctors);
        return "consult";
    }

    @PostMapping("/consult")
    public String doConsult(@RequestParam Long doctorId,
                             @RequestParam String disease,
                             @RequestParam String symptoms,
                             @RequestParam String date,
                             HttpSession session,
                             Model model) {
        try {
            // Ambil ID pasien dari session
            Object patientId = session.getAttribute("patientId");
            if (patientId == null) {
                model.addAttribute("message", "Anda harus login sebagai pasien untuk melakukan konsultasi.");
                return "consult-result";
            }

            // Validasi input
            if (doctorId == null || disease == null || symptoms == null || date == null) {
                model.addAttribute("message", "Semua field harus diisi.");
                return "consult";
            }
            
            // Parsing tanggal dengan format yang sesuai
            LocalDate ld = LocalDate.parse(date);
            Consultation c = userService.createConsultation((Long)patientId, doctorId, disease, symptoms, ld);
            model.addAttribute("message", "Konsultasi berhasil dijadwalkan dengan ID: " + c.getId());
            return "consult-result";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Terjadi error: " + e.getMessage());
            return "consult";
        }
    }

    @GetMapping("/schedule")
    public String viewSchedule(@RequestParam(required = false) Long doctorId, Model model) {
        if (doctorId == null) {
            model.addAttribute("message", "Doctor ID tidak ditemukan.");
            return "consult-result";
        }
        try {
            var doctor = userService.getDoctorWithConsultations(doctorId);
            model.addAttribute("consultations", doctor.getConsultationsReceived());
            return "schedule";
        } catch (Exception e) {
            model.addAttribute("message", "Terjadi error: " + e.getMessage());
            return "consult-result";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!model.containsAttribute("userType")) {
            Object userType = session.getAttribute("userType");
            model.addAttribute("userType", userType != null ? userType : "User");
        }
        if (!model.containsAttribute("name")) {
            Object name = session.getAttribute("name");
            model.addAttribute("name", name != null ? name : "");
        }
        if (!model.containsAttribute("doctorId")) {
            Object doctorId = session.getAttribute("doctorId");
            if (doctorId != null) {
                model.addAttribute("doctorId", doctorId);
            }
        }

        // Tambahkan data statistik
        long totalDoctors = userService.getTotalDoctors();
        long totalPatients = userService.getTotalPatients();
        long totalConsultations = userService.getTotalConsultations();
        
        System.out.println("Total Doctors: " + totalDoctors);
        System.out.println("Total Patients: " + totalPatients);
        System.out.println("Total Consultations: " + totalConsultations);
        
        model.addAttribute("totalDoctors", totalDoctors);
        model.addAttribute("totalPatients", totalPatients);
        model.addAttribute("totalConsultations", totalConsultations);
        
        // Jika user adalah dokter, tambahkan jumlah jadwal mereka
        Object doctorId = session.getAttribute("doctorId");
        if (doctorId != null) {
            long scheduleCount = userService.getDoctorScheduleCount((Long) doctorId);
            System.out.println("Schedule Count for Doctor " + doctorId + ": " + scheduleCount);
            model.addAttribute("scheduleCount", scheduleCount);
        }

        return "dashboard";
    }
}