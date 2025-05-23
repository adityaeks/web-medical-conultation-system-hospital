package com.medical.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    // ...existing code...

    @GetMapping("/admin/fix-bug")
    public String fixBugPage(@RequestParam(name = "name", required = false, defaultValue = "Admin") String name, Model model) {
        model.addAttribute("adminName", name);
        return "fix-bug";
    }

    // ...existing code...
}