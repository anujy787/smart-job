package edu.neu.csye7374.smartjob.controller;

import edu.neu.csye7374.smartjob.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/signup/process")
    public String processSignup(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role,
            // Optional job seeker specific fields
            @RequestParam(required = false) String skills,
            @RequestParam(required = false) String experience,
            @RequestParam(required = false) String education,
            // Optional employer specific fields
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String companySize,
            RedirectAttributes redirectAttributes) {
        
        try {
            // Use the service to register the appropriate user type
            if (role.equals("JOB_SEEKER")) {
                userService.registerJobSeeker(firstName, lastName, email, password, 
                                            skills, experience, education);
            } else if (role.equals("EMPLOYER")) {
                userService.registerEmployer(firstName, lastName, email, password,
                                           companyName, industry, companySize);
            } else {
                throw new IllegalArgumentException("Invalid role: " + role);
            }
            
            // Add success message and redirect to login
            redirectAttributes.addFlashAttribute("successMessage", "Account created successfully! Please login.");
            return "redirect:/login";
            
        } catch (Exception e) {
            // Add error message and redirect back to signup
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating account: " + e.getMessage());
            return "redirect:/signup";
        }
    }
} 