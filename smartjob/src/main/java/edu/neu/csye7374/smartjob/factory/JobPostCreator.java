package edu.neu.csye7374.smartjob.factory;

import edu.neu.csye7374.smartjob.model.JobPost;

public interface JobPostCreator {
	
	JobPost createJob(JobPost inp);
}
