package edu.neu.csye7374.smartjob.commands;

import edu.neu.csye7374.smartjob.model.JobPost;

public interface JobPostCommand {
	
	void execute(JobPost jobPost);
}
