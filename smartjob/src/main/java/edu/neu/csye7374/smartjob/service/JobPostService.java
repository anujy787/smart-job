package edu.neu.csye7374.smartjob.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.neu.csye7374.smartjob.factory.JobPostCreator;
import edu.neu.csye7374.smartjob.factory.JobPostFactory;
import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.repository.JobPostRepository;

@Service
public class JobPostService {
	
	private final JobPostRepository jobPostRepo;
	
	@Autowired
	public JobPostService(JobPostRepository jobPostRepo) {
		this.jobPostRepo = jobPostRepo;
	}
	
	public JobPost createJob(JobPost inp) {
		JobPostCreator creator = JobPostFactory.getCreator(inp.getJobType());
		JobPost jobPost = creator.createJob(inp);
		return jobPostRepo.save(jobPost);
	}
	
	public void updateJobPost(JobPost updatedJob) {
		JobPost existing = jobPostRepo.findById(updatedJob.getId()).orElseThrow(() -> new RuntimeException("Job post not found"));
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
	
	
}
