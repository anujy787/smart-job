package edu.neu.csye7374.smartjob.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.neu.csye7374.smartjob.factory.JobPostCreator;
import edu.neu.csye7374.smartjob.factory.JobPostFactory;
import edu.neu.csye7374.smartjob.factory.SearchStrategyFactory;
import edu.neu.csye7374.smartjob.model.JobApplication;
import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.model.Notification;
import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.model.UserRole;
import edu.neu.csye7374.smartjob.observer.NotificationSubject;
import edu.neu.csye7374.smartjob.repository.JobApplicationRepository;
import edu.neu.csye7374.smartjob.repository.JobPostRepository;
import edu.neu.csye7374.smartjob.repository.UserRepository;
import edu.neu.csye7374.smartjob.strategy.SearchContext;
import jakarta.transaction.Transactional;

@Service
public class JobPostService {
    @Autowired
    private JobPostRepository jobPostRepo;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationSubject notificationSubject;

    public List<JobPost> getAllJobs() {
        List<JobPost> jobs = jobPostRepo.findAll();
        return jobs != null ? jobs : java.util.Collections.emptyList();
    }
    
    public List<JobPost> getJobsByUser(User user) {
        return jobPostRepo.findByUserOrderByPostedDateDesc(user);
    }
    
    @Transactional
    public JobPost createJob(JobPost inp) {
        JobPostCreator creator = JobPostFactory.getCreator(inp.getJobType());
        JobPost jobPost = creator.createJob(inp);
        JobPost savedJob = jobPostRepo.save(jobPost);
        
        // Notify all job seekers about the new job posting
        List<User> jobSeekers = userRepository.findByRole(UserRole.JOB_SEEKER);
        for (User jobSeeker : jobSeekers) {
            Notification notification = new Notification();
            notification.setUser(jobSeeker);
            notification.setMessage("New job posted: " + savedJob.getTitle() + " at " + savedJob.getCompanyName());
            notification.setNotificationType("JOB_POSTED");
            notificationSubject.notifyObservers(notification);
        }
        
        return savedJob;
    }
    
    public void updateJobPost(JobPost updatedJob) {
        JobPost existing = jobPostRepo.findById(updatedJob.getJobId())
            .orElseThrow(() -> new RuntimeException("Job post not found"));
        existing.setTitle(updatedJob.getTitle());
        existing.setCompanyName(updatedJob.getCompanyName());
        existing.setLocation(updatedJob.getLocation());
        existing.setSalaryRange(updatedJob.getSalaryRange());
        existing.setJobType(updatedJob.getJobType());
        existing.setJobDescription(updatedJob.getJobDescription());
        existing.setJobRequirements(updatedJob.getJobRequirements());
        existing.setJobResponsibilities(updatedJob.getJobResponsibilities());
        jobPostRepo.save(existing);
    }
    
    public JobPost findById(Long id) {
        return jobPostRepo.findById(id).orElse(null);
    }
    
    @Transactional
    public Map<String, Object> deleteJobById(Long jobId) {
        Map<String, Object> response = new HashMap<>();
        try {
            JobPost jobPost = jobPostRepo.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job post not found with id: " + jobId));

            List<JobApplication> applications = jobApplicationRepository.findByJobPost_JobId(jobId);
            if (!applications.isEmpty()) {
                jobApplicationRepository.deleteAll(applications);
            }
            
            jobPostRepo.delete(jobPost);
            
            // Notify all job seekers about the job deletion
            List<User> jobSeekers = userRepository.findByRole(UserRole.JOB_SEEKER);
            for (User jobSeeker : jobSeekers) {
                Notification notification = new Notification();
                notification.setUser(jobSeeker);
                notification.setMessage("Job posting terminated: " + jobPost.getTitle() + " at " + jobPost.getCompanyName());
                notification.setNotificationType("JOB_DELETED");
                notificationSubject.notifyObservers(notification);
            }
            
            response.put("success", true);
            response.put("message", "Job post and " + applications.size() + " application(s) deleted successfully.");
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to delete job post: " + e.getMessage());
        }
        return response;
    }
    
    public List<JobPost> searchUserJobs(User user, String searchTerm, String searchType) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }
        if (searchType == null || searchType.trim().isEmpty()) {
            throw new IllegalArgumentException("Search type cannot be empty");
        }

        List<JobPost> userJobs = jobPostRepo.findByUserOrderByPostedDateDesc(user);
        
        SearchContext searchContext = new SearchContext();
        try {
            searchContext.setSearchStrategy(SearchStrategyFactory.getStrategy(searchType));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid search type: " + searchType);
        }
        
        return searchContext.executeSearch(userJobs, searchTerm);
    }
}
