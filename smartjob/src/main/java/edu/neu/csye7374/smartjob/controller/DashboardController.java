package edu.neu.csye7374.smartjob.controller;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.service.JobPostService;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
	
	@Autowired
	private  JobPostService jobPostService;
    
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
    	User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        List<JobPost> jobPost = jobPostService.getJobsByUser(user);
        System.out.println(jobPost);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        List<Map<String, Object>> formattedJobPosts = jobPost.stream()
            .map(job -> {
                Map<String, Object> map = new HashMap<>();
                map.put("title", job.getTitle());
                map.put("companyName", job.getCompanyName());
                map.put("location", job.getLocation());
                map.put("postedDate", job.getPostedDate().format(formatter));
                return map;
            })
            .collect(Collectors.toList());

        model.addAttribute("jobPost", formattedJobPosts);
        return "dashboard";
    }
    
    
} 