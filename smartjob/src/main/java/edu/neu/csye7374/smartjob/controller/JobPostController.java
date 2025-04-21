 package edu.neu.csye7374.smartjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import edu.neu.csye7374.smartjob.invokers.JobPostInvoker;
import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.service.JobPostService;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/jobs")
public class JobPostController {
	
	@Autowired
	private  JobPostService jobPostService;
	
	@Autowired
	private  JobPostInvoker jobPostInvoker;
	
	
//	public JobPostController(JobPostService jobPostService,JobPostInvoker jobPostInvoker ) {
//		this.jobPostService = jobPostService;
//		this.jobPostInvoker = jobPostInvoker;
//	}
	
	@GetMapping("/create")
    public String showCreateJobForm(Model model) {
        model.addAttribute("jobPost", new JobPost());
        return "post-job";
    }
	
	@PostMapping("/create")
    public String createJob(@ModelAttribute JobPost jobPost, HttpSession session,RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirectAttributes.addFlashAttribute("error", "Please log in to post a job.");
            return "redirect:/login";
        }
		jobPost.setUser(user);
		jobPostService.createJob(jobPost);
		redirectAttributes.addFlashAttribute("success", "Job posted successfully.");
	    return "redirect:/dashboard";
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody JobPost jobPost) {
        jobPost.setJobId(id);
        jobPostInvoker.invokeUpdate(jobPost);
        return ResponseEntity.ok("Job post updated successfully.");
    }
}
