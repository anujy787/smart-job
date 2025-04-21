package edu.neu.csye7374.smartjob.controller;

import edu.neu.csye7374.smartjob.model.JobApplication;
import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.model.JobSeeker;
import edu.neu.csye7374.smartjob.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/apply")
    public JobApplication applyForJob(@RequestBody JobSeeker jobSeeker, @RequestBody JobPost jobPost) {
        return applicationService.applyForJob(jobSeeker, jobPost);
    }

    @PostMapping("/withdraw/{id}")
    public boolean withdrawApplication(@PathVariable Long id) {
        return applicationService.withdrawApplication(id);
    }

    @GetMapping("/jobseeker/{id}")
    public List<JobApplication> getApplicationsByJobSeeker(@PathVariable Long id) {
        JobSeeker jobSeeker = new JobSeeker(); // This should be fetched from the database
        jobSeeker.setId(id);
        return applicationService.getApplicationsByJobSeeker(jobSeeker);
    }
} 