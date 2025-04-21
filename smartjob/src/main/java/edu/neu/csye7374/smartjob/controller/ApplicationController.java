package edu.neu.csye7374.smartjob.controller;

import edu.neu.csye7374.smartjob.model.JobApplication;
import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.model.JobSeeker;
import edu.neu.csye7374.smartjob.service.ApplicationService;
import edu.neu.csye7374.smartjob.service.JobPostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private JobPostService jobPostService;

    @PostMapping("/apply")
    public JobApplication applyForJob(@RequestBody JobSeeker jobSeeker, @RequestBody JobPost jobPost) {
        return applicationService.applyForJob(jobSeeker, jobPost);
    }

    // NEW: Handle job application from job details form
    @PostMapping("/apply/{jobId}")
    public String applyForJobFromDetails(@PathVariable Long jobId, HttpSession session, Model model) {
        Object userObj = session.getAttribute("user");
        if (userObj == null || !(userObj instanceof edu.neu.csye7374.smartjob.model.JobSeeker)) {
            model.addAttribute("error", "Please log in as a job seeker to apply.");
            return "redirect:/login";
        }
        edu.neu.csye7374.smartjob.model.JobSeeker jobSeeker = (edu.neu.csye7374.smartjob.model.JobSeeker) userObj;
        edu.neu.csye7374.smartjob.model.JobPost jobPost = jobPostService.findById(jobId);
        if (jobPost == null) {
            model.addAttribute("error", "Job not found.");
            return "redirect:/browse-jobs";
        }
        applicationService.applyForJob(jobSeeker, jobPost);
        return "redirect:/applications";
    }

    @PostMapping("/withdraw/{id}")
    public String withdrawApplication(@PathVariable Long id) {
        applicationService.withdrawApplication(id);
        return "redirect:/applications";
    }

    @GetMapping("/jobseeker/{id}")
    public List<JobApplication> getApplicationsByJobSeeker(@PathVariable Long id) {
        JobSeeker jobSeeker = new JobSeeker(); // This should be fetched from the database
        jobSeeker.setId(id);
        return applicationService.getApplicationsByJobSeeker(jobSeeker);
    }
}