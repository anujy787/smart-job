 package edu.neu.csye7374.smartjob.controller;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.neu.csye7374.smartjob.strategy.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import org.springframework.web.bind.annotation.RequestParam;

import edu.neu.csye7374.smartjob.strategy.SearchContext;


@Controller
@RequestMapping("/jobs")
public class JobPostController {

    // /browse-jobs mapping moved to new controller

    @GetMapping("/{id}")
    public String jobDetails(@PathVariable Long id, Model model) {
        JobPost job = jobPostService.findById(id);
        if (job == null) {
            model.addAttribute("error", "Job not found");
            return "redirect:/browse-jobs";
        }
        model.addAttribute("job", job);
        return "job-details";
    }
	
	@Autowired
	private  JobPostService jobPostService;
	
	@Autowired
	private  JobPostInvoker jobPostInvoker;
	
	
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
	
	@GetMapping("/edit/{id}")
    public String showEditJobForm(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        JobPost jobPost = jobPostService.findById(id);

        if (jobPost == null || !jobPost.getUser().getId().equals(user.getId())) {
            redirectAttributes.addFlashAttribute("error", "Unauthorized access.");
            return "redirect:/dashboard";
        }

        model.addAttribute("jobPost", jobPost);
        return "edit-job";  
    }
	
	@PostMapping("/update/{id}")
    public String updateJob(@PathVariable Long id, @ModelAttribute JobPost jobPost, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in to update the job.");
            return "redirect:/login";
        }

        jobPost.setJobId(id);
        jobPost.setUser(user);  
        jobPostInvoker.invokeUpdate(jobPost);
        redirectAttributes.addFlashAttribute("success", "Job post updated successfully.");
        return "redirect:/dashboard";
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody JobPost jobPost) {
//        jobPost.setJobId(id);
//        jobPostInvoker.invokeUpdate(jobPost);
//        return ResponseEntity.ok("Job post updated successfully.");
//    }
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        try {
            JobPost jobPost = jobPostService.findById(id);

            if (jobPost == null) {
            throw new RuntimeException("Job post not found");
            }
            
            Map<String, Object> result = jobPostInvoker.invokeDelete(jobPost);

            if ((Boolean) result.get("success")) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.internalServerError().body(result);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("success", "false");
            response.put("message", "An error occurred while deleting the job post.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
	}


    @GetMapping("/search")
    public ResponseEntity<?> searchJobs(@RequestParam String searchTerm, @RequestParam String searchType, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Please log in to search jobs.");
        }

        try {
            // Perform search
            List<JobPost> searchResults = jobPostService.searchUserJobs(user, searchTerm, searchType);
            
            // Format results
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            List<Map<String, Object>> formattedResults = searchResults.stream()
                .map(job -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jobId", job.getJobId());
                    map.put("title", job.getTitle());
                    map.put("companyName", job.getCompanyName());
                    map.put("location", job.getLocation());
                    map.put("postedDate", job.getPostedDate().format(formatter));
                    return map;
                })
                .collect(Collectors.toList());

            return ResponseEntity.ok(formattedResults);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while searching jobs");
        }
    }
}
