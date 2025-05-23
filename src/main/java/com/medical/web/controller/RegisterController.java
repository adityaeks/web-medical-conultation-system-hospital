package com.medical.web.controller;

import com.medical.web.model.Doctor;
import com.medical.web.model.Patient;
import com.medical.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String userType,
                             @RequestParam String name,
                             @RequestParam String gmail,
                             @RequestParam String password,
                             Model model) {
        if (userType.equals("Patient")) {
            Patient p = userService.registerPatient(name, gmail, password);
            model.addAttribute("message", "Patient registered: " + p.getName());
        } else {
            Doctor d = userService.registerDoctor(name, gmail, password);
            model.addAttribute("message", "Doctor registered: " + d.getName());
        }
        return "register-success";
    }
}