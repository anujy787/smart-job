package edu.neu.csye7374.smartjob.service;

import edu.neu.csye7374.smartjob.model.JobApplication;
import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.model.JobSeeker;
import edu.neu.csye7374.smartjob.model.User;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import edu.neu.csye7374.smartjob.repository.JobApplicationRepository;

@Service
public class ApplicationService {
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private edu.neu.csye7374.smartjob.service.JobPostService jobPostService;

    public JobApplication applyForJob(JobSeeker jobSeeker, JobPost jobPost) {
        JobApplication application = new JobApplication(
            null, // Let the DB handle the ID
            jobSeeker,
            jobPost,
            "APPLIED",
            new Date(),
            new Date()
        );
        // Use State pattern to invoke apply logic if needed
        application.getStateObj().apply(application);
        return jobApplicationRepository.save(application);
    }

    public boolean withdrawApplication(Long applicationId) {
        return jobApplicationRepository.findById(applicationId).map(application -> {
            String state = application.getState();
            if ("APPLIED".equals(state) || "IN-REVIEW".equals(state)) {
                application.getStateObj().withdraw(application);
                jobApplicationRepository.save(application);
                return true;
            }
            return false;
        }).orElse(false);
    }

    // Employer actions
    public boolean setInReview(Long applicationId) {
        return jobApplicationRepository.findById(applicationId).map(application -> {
            String state = application.getState();
            if ("APPLIED".equals(state)) {
                if (application.getStateObj() instanceof edu.neu.csye7374.smartjob.service.state.AppliedState) {
                    ((edu.neu.csye7374.smartjob.service.state.AppliedState)application.getStateObj()).inReview(application);
                    jobApplicationRepository.save(application);
                    return true;
                }
            }
            return false;
        }).orElse(false);
    }

    public boolean setHired(Long applicationId) {
        return jobApplicationRepository.findById(applicationId).map(application -> {
            String state = application.getState();
            if ("IN-REVIEW".equals(state) || "APPLIED".equals(state)) {
                application.getStateObj().hire(application);
                application.setState("HIRED"); // Ensure persistent state is set
                jobApplicationRepository.save(application);
                return true;
            }
            return false;
        }).orElse(false);
    }

    public boolean setRejected(Long applicationId) {
        return jobApplicationRepository.findById(applicationId).map(application -> {
            String state = application.getState();
            if ("IN-REVIEW".equals(state) || "APPLIED".equals(state)) {
                application.getStateObj().reject(application);
                jobApplicationRepository.save(application);
                return true;
            }
            return false;
        }).orElse(false);
    }

    public List<JobApplication> getApplicationsByJobSeeker(JobSeeker jobSeeker) {
        return jobApplicationRepository.findByJobSeekerId(jobSeeker.getId());
    }

    // New method for direct id-based lookup
    public List<JobApplication> getApplicationsByJobSeekerId(Long jobSeekerId) {
        return jobApplicationRepository.findByJobSeekerId(jobSeekerId);
    }

    // Get all jobs posted by an employer with their applications
    public List<JobPostWithApplications> getJobsWithApplicationsByEmployer(User employer) {
        List<JobPost> jobs = jobPostService.getJobsByUser(employer);
        List<JobPostWithApplications> result = new java.util.ArrayList<>();
        for (JobPost job : jobs) {
            List<JobApplication> apps = jobApplicationRepository.findByJobPost_JobId(job.getJobId());
            result.add(new JobPostWithApplications(job, apps));
        }
        return result;
    }

    public static class JobPostWithApplications {
        public JobPost jobPost;
        public List<JobApplication> applications;
        public JobPostWithApplications(JobPost jobPost, List<JobApplication> applications) {
            this.jobPost = jobPost;
            this.applications = applications;
        }
    }
} 