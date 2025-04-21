package edu.neu.csye7374.smartjob.controller;

import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class BrowseJobsController {
    @Autowired
    private JobPostService jobPostService;

    @GetMapping("/browse-jobs")
    public String browseJobs(Model model) {
        try {
            List<JobPost> jobs = jobPostService.getAllJobs();
            if (jobs == null) jobs = java.util.Collections.emptyList();
            model.addAttribute("jobs", jobs);
            return "browse-jobs";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Could not load jobs: " + e.getMessage());
            return "error";
        }
    }
}
