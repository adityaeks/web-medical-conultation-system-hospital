package com.medical.web.controller;

import com.medical.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetPasswordController {

    @Autowired private UserService userService;

    @GetMapping("/reset-password")
    public String showResetForm() {
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String doReset(@RequestParam String userType,
                          @RequestParam String name,
                          @RequestParam String gmail,
                          @RequestParam String newPassword,
                          Model model) {
        boolean success;
        if (userType.equals("Patient")) {
            success = userService.resetPatientPassword(name, gmail, newPassword);
        } else {
            success = userService.resetDoctorPassword(name, gmail, newPassword);
        }
        model.addAttribute("message", success ? "Password reset successfully" : "Reset failed");
        return "reset-result";
    }
}