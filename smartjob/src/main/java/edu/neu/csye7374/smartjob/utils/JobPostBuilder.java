package edu.neu.csye7374.smartjob.utils;

import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.model.User;

public class JobPostBuilder {
	
	private final JobPost jobPost;

    public JobPostBuilder() {
        jobPost = new JobPost();
    }

    public JobPostBuilder setTitle(String title) {
        jobPost.setTitle(title); return this;
    }

    public JobPostBuilder setUser(User user) {
        jobPost.setUser(user); return this;
    }

    public JobPostBuilder setCompanyName(String companyName) {
        jobPost.setCompanyName(companyName); return this;
    }

    public JobPostBuilder setLocation(String location) {
        jobPost.setLocation(location); return this;
    }

    public JobPostBuilder setSalaryRange(String salaryRange) {
        jobPost.setSalaryRange(salaryRange); return this;
    }

    public JobPostBuilder setJobType(String jobType) {
        jobPost.setJobType(jobType); return this;
    }

    public JobPostBuilder setJobDescription(String jobDescription) {
        jobPost.setJobDescription(jobDescription); return this;
    }

    public JobPostBuilder setJobRequirements(String jobRequirements) {
        jobPost.setJobRequirements(jobRequirements); return this;
    }

    public JobPostBuilder setJobResponsibilities(String jobResponsibilities) {
        jobPost.setJobResponsibilities(jobResponsibilities); return this;
    }

    public JobPost build() {
        return jobPost;
    }

}
