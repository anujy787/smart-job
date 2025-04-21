package edu.neu.csye7374.smartjob.controller;

import edu.neu.csye7374.smartjob.model.JobApplication;
import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.service.ApplicationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmployerApplicationsController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/manage-applications")
    public String manageApplications(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"EMPLOYER".equals(user.getRole().name())) {
            return "redirect:/login";
        }
        List<ApplicationService.JobPostWithApplications> jobsWithApps = applicationService.getJobsWithApplicationsByEmployer(user);
        model.addAttribute("jobsWithApps", jobsWithApps);
        return "manage-applications";
    }
}
