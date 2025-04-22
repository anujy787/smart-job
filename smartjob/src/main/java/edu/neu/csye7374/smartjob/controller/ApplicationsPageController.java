package edu.neu.csye7374.smartjob.controller;

import edu.neu.csye7374.smartjob.model.JobApplication;
import edu.neu.csye7374.smartjob.model.JobSeeker;
import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/applications/search")
    public ResponseEntity<?> searchApplications(
            @RequestParam String searchTerm,
            @RequestParam String searchType,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Please log in to search applications.");
        }

        try {

            System.out.println("SEARCH APPS: term=" + searchTerm + " type=" + searchType
                    + " userId=" + user.getId());

            List<JobApplication> results = applicationService.searchUserApplications(user, searchTerm, searchType);

            // format dates
            SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
            List<Map<String, Object>> out = results.stream().map(app -> {
                Map<String, Object> m = new HashMap<>();
                m.put("id", app.getId());
                m.put("title", app.getJobPost().getTitle());
                m.put("companyName", app.getJobPost().getCompanyName());
                m.put("location", app.getJobPost().getLocation());
                m.put("applicationDate", fmt.format(app.getApplicationDate()));
                m.put("state", app.getState());
                return m;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(out);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("An error occurred while searching applications");
        }
    }
}