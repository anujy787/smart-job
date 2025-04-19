package edu.neu.csye7374.smartjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }
} 