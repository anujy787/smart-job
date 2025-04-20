package edu.neu.csye7374.smartjob.invokers;

import org.springframework.stereotype.Component;

import edu.neu.csye7374.smartjob.commands.JobPostCommand;
import edu.neu.csye7374.smartjob.model.JobPost;


@Component
public class JobPostInvoker {
	
	private final JobPostCommand updateJobPostCommand;
	
	public JobPostInvoker(JobPostCommand updateJobPostCommand) {
		this.updateJobPostCommand = updateJobPostCommand;
	}
	
	public void invokeUpdate(JobPost jobPost) {
		updateJobPostCommand.execute(jobPost);
	}
}
