 package edu.neu.csye7374.smartjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.csye7374.smartjob.invokers.JobPostInvoker;
import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.service.JobPostService;


@Controller
@RequestMapping("/api/jobs")
public class JobPostController {
	
	@Autowired
	private  JobPostService jobPostService;
	
	@Autowired
	private  JobPostInvoker jobPostInvoker;
	
	
//	public JobPostController(JobPostService jobPostService,JobPostInvoker jobPostInvoker ) {
//		this.jobPostService = jobPostService;
//		this.jobPostInvoker = jobPostInvoker;
//	}
	
	@PostMapping
    public ResponseEntity<JobPost> createJob(@RequestBody JobPost jobPost) {
        JobPost saved = jobPostService.createJob(jobPost);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody JobPost jobPost) {
        jobPost.setId(id);
        jobPostInvoker.invokeUpdate(jobPost);
        return ResponseEntity.ok("Job post updated successfully.");
    }
}
