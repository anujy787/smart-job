package edu.neu.csye7374.smartjob.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.service.JobPostService;


@Component
public class UpdateJobPostCommand implements JobPostCommand{
	
	private final JobPostService jobPostService;
	
	@Autowired
	public UpdateJobPostCommand(JobPostService jobPostService) {
		this.jobPostService = jobPostService;
	}
	
	@Override
	public void execute(JobPost jobPost) {
		jobPostService.updateJobPost(jobPost);
	}
	
}
