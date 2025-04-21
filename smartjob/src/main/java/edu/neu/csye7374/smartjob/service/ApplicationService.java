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

    @Autowired
    private edu.neu.csye7374.smartjob.service.EmailService emailService;

    private edu.neu.csye7374.smartjob.service.email.JobApplicationEmailAdapter jobApplicationEmailAdapter;

    @Autowired
    public ApplicationService(JobApplicationRepository jobApplicationRepository,
                             edu.neu.csye7374.smartjob.service.JobPostService jobPostService,
                             edu.neu.csye7374.smartjob.service.EmailService emailService) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobPostService = jobPostService;
        this.emailService = emailService;
        this.jobApplicationEmailAdapter = new edu.neu.csye7374.smartjob.service.email.JobApplicationEmailAdapter(emailService);
    }

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
        jobApplicationRepository.save(application);
        // Send applied email
        jobApplicationEmailAdapter.sendApplicationStatusEmail(application, "APPLIED");
        return application;
    }

    public boolean withdraw(Long applicationId) {
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
                // Send hired email
                jobApplicationEmailAdapter.sendApplicationStatusEmail(application, "HIRED");
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
                // Send rejected email
                jobApplicationEmailAdapter.sendApplicationStatusEmail(application, "REJECTED");
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

    // Check if a job seeker has already applied for a job post
    public boolean hasApplied(Long jobSeekerId, Long jobId) {
        return jobApplicationRepository.existsByJobSeeker_IdAndJobPost_JobId(jobSeekerId, jobId);
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