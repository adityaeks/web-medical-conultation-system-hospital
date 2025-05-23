package com.medical.web.controller;

import com.medical.web.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired private UserService userService;

    // tampilkan form login
    @GetMapping("/")
    public String showLoginForm(Model model) {
        return "index";
    }

    // proses login
    @PostMapping("/login")
    public String doLogin(@RequestParam String userType,
                          @RequestParam String name,
                          @RequestParam String password,
                          Model model,
                          HttpSession session) {
        boolean ok;
        switch (userType) {
            case "Admin":   ok = userService.adminLogin(name, password);  break;
            case "Patient": ok = userService.patientLogin(name, password);break;
            case "Doctor":  ok = userService.doctorLogin(name, password); break;
            default: ok = false;
        }
        if (ok) {
            model.addAttribute("userType", userType);
            model.addAttribute("name", name);
            session.setAttribute("userType", userType);
            session.setAttribute("name", name);
            
            // Tambahkan ID ke session berdasarkan tipe user
            if ("Doctor".equals(userType)) {
                Long doctorId = userService.getDoctorIdByName(name);
                System.out.println("DEBUG: doctorId for " + name + " = " + doctorId);
                model.addAttribute("doctorId", doctorId);
                session.setAttribute("doctorId", doctorId);
            } else if ("Patient".equals(userType)) {
                Long patientId = userService.getPatientIdByName(name);
                System.out.println("DEBUG: patientId for " + name + " = " + patientId);
                model.addAttribute("patientId", patientId);
                session.setAttribute("patientId", patientId);
            }
            return "dashboard";
        }
        model.addAttribute("error", "Invalid credentials");
        return "index";
    }
}