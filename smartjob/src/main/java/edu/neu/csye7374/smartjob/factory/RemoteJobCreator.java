package edu.neu.csye7374.smartjob.factory;

import java.time.LocalDateTime;

import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.utils.JobPostBuilder;

public class RemoteJobCreator implements JobPostCreator {
	
	 @Override
	    public JobPost createJob(JobPost inp) {
	        return new JobPostBuilder()
	                .setTitle(inp.getTitle())
	                .setCompanyName(inp.getCompanyName())
	                .setLocation( inp.getLocation() != null ? inp.getLocation() :  "Remote")  
	                .setSalaryRange(inp.getSalaryRange() != null ? inp.getSalaryRange() : "Flexible")
	                .setJobType("Remote")
	                .setJobDescription(inp.getJobDescription())
	                .setJobRequirements(inp.getJobRequirements())
	                .setJobResponsibilities(inp.getJobResponsibilities())
	                .setUser(inp.getUser()).setPostedDate(LocalDateTime.now())
					.setStatus("ACTIVE").build();
	    }

}
