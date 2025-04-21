package edu.neu.csye7374.smartjob.invokers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.csye7374.smartjob.commands.DeletePostCommand;
import edu.neu.csye7374.smartjob.commands.JobPostCommand;
import edu.neu.csye7374.smartjob.model.JobPost;


@Component
public class JobPostInvoker {
	
	@Autowired
	private DeletePostCommand deletePostCommand;

	
	private final JobPostCommand updateJobPostCommand;
	
	public JobPostInvoker(JobPostCommand updateJobPostCommand) {
		this.updateJobPostCommand = updateJobPostCommand;
	}
	
	public void invokeUpdate(JobPost jobPost) {
		updateJobPostCommand.execute(jobPost);
	}
	

	public Map<String, Object> invokeDelete(JobPost jobPost) {
	    return deletePostCommand.executeDelete(jobPost);
	}
	
}
