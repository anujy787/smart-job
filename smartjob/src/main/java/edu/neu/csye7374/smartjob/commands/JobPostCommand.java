package edu.neu.csye7374.smartjob.commands;

import java.util.Map;

import edu.neu.csye7374.smartjob.model.JobPost;

public interface JobPostCommand {
	
	void execute(JobPost jobPost);

	Map<String, Object> executeDelete(JobPost jobPost);
}
