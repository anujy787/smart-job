package edu.neu.csye7374.smartjob.factory;

import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.utils.JobPostBuilder;

public class PartTimeJobCreator implements JobPostCreator {
	
	 @Override
	    public JobPost createJob(JobPost inp) {
	        return new JobPostBuilder()
	                .setTitle(inp.getTitle())
	                .setCompanyName(inp.getCompanyName())
	                .setLocation(inp.getLocation())
	                .setSalaryRange(inp.getSalaryRange() != null ? inp.getSalaryRange() : "Hourly Rate")
	                .setJobType("Part-time")
	                .setJobDescription(inp.getJobDescription())
	                .setJobRequirements(inp.getJobRequirements())
	                .setJobResponsibilities(inp.getJobResponsibilities())
	                .setUser(inp.getUser()).build();
	    }
}
