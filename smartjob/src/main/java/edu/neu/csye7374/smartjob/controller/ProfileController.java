package edu.neu.csye7374.smartjob.controller;

import edu.neu.csye7374.smartjob.model.JobSeeker;
import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Fetch fresh data from database instead of using session data
        JobSeeker jobSeeker = (JobSeeker) userService.getUserById(user.getId());
        
        model.addAttribute("firstName", jobSeeker.getFirstName());
        model.addAttribute("lastName", jobSeeker.getLastName());
        model.addAttribute("email", jobSeeker.getEmail());
        model.addAttribute("skills", jobSeeker.getSkills());
        model.addAttribute("experience", jobSeeker.getExperience());
        model.addAttribute("education", jobSeeker.getEducation());
        model.addAttribute("resumeUrl", jobSeeker.getResumeUrl());
        
        return "jobseeker-profile";
    }
} 