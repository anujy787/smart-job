package edu.neu.csye7374.smartjob.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.neu.csye7374.smartjob.factory.JobPostCreator;
import edu.neu.csye7374.smartjob.factory.JobPostFactory;
import edu.neu.csye7374.smartjob.factory.SearchStrategyFactory;
import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.repository.JobPostRepository;
import edu.neu.csye7374.smartjob.strategy.SearchContext;

@Service
public class JobPostService {
    public List<JobPost> getAllJobs() {
        // Directly fetch all jobs from the job_posts table
        List<JobPost> jobs = jobPostRepo.findAll();
        return jobs != null ? jobs : java.util.Collections.emptyList();
    }
	
	private final JobPostRepository jobPostRepo;
	
	@Autowired
	public JobPostService(JobPostRepository jobPostRepo) {
		this.jobPostRepo = jobPostRepo;
	}
	
	public List<JobPost> getJobsByUser(User user) {
        return jobPostRepo.findByUserOrderByPostedDateDesc(user);
    }
	
	public JobPost createJob(JobPost inp) {
		JobPostCreator creator = JobPostFactory.getCreator(inp.getJobType());
		JobPost jobPost = creator.createJob(inp);
		return jobPostRepo.save(jobPost);
	}
	
	public void updateJobPost(JobPost updatedJob) {
		JobPost existing = jobPostRepo.findById(updatedJob.getJobId()).orElseThrow(() -> new RuntimeException("Job post not found"));
		existing.setTitle(updatedJob.getTitle());
        existing.setCompanyName(updatedJob.getCompanyName());
        existing.setLocation(updatedJob.getLocation());
        existing.setSalaryRange(updatedJob.getSalaryRange());
        existing.setJobType(updatedJob.getJobType());
        existing.setJobDescription(updatedJob.getJobDescription());
        existing.setJobRequirements(updatedJob.getJobRequirements());
        existing.setJobResponsibilities(updatedJob.getJobResponsibilities());
        jobPostRepo.save(existing);
	}
	
	public JobPost findById(Long id) {
	    return jobPostRepo.findById(id).orElse(null);
	}
	
	public void deleteJobById(Long id) {
	    jobPostRepo.deleteById(id);
	}
	
	public List<JobPost> searchUserJobs(User user, String searchTerm, String searchType) {
        // Validate inputs
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }
        if (searchType == null || searchType.trim().isEmpty()) {
            throw new IllegalArgumentException("Search type cannot be empty");
        }

        // Get user's jobs
        List<JobPost> userJobs = jobPostRepo.findByUserOrderByPostedDateDesc(user);
        
        // Create and configure search context
        SearchContext searchContext = new SearchContext();
        try {
            searchContext.setSearchStrategy(SearchStrategyFactory.getStrategy(searchType));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid search type: " + searchType);
        }
        
        // Execute search
        return searchContext.executeSearch(userJobs, searchTerm);
    }
	
}
