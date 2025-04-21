package edu.neu.csye7374.smartjob.service;

import edu.neu.csye7374.smartjob.model.JobApplication;
import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.model.JobSeeker;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import edu.neu.csye7374.smartjob.repository.JobApplicationRepository;

@Service
public class ApplicationService {
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

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
            if ("APPLIED".equals(application.getState())) {
                application.getStateObj().withdraw(application);
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
} 