package edu.neu.csye7374.smartjob.controller;

import edu.neu.csye7374.smartjob.model.JobApplication;
import edu.neu.csye7374.smartjob.model.JobSeeker;
import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ApplicationsPageController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/applications")
    public String showUserApplications(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        try {
            // Fetch applications directly by user id (job_seeker_id)
            List<JobApplication> applications = applicationService.getApplicationsByJobSeekerId(user.getId());
            model.addAttribute("applications", applications);
        } catch (Exception e) {
            // Log error for debugging
            e.printStackTrace();
            model.addAttribute("error", "Could not load your applications: " + e.getMessage());
        }
        return "applications";
    }
}
