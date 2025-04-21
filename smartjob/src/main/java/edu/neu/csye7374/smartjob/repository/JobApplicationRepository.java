package edu.neu.csye7374.smartjob.repository;

import edu.neu.csye7374.smartjob.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJobSeekerId(Long jobSeekerId);
    List<JobApplication> findByJobPost_JobId(Long jobId);
    boolean existsByJobSeeker_IdAndJobPost_JobId(Long jobSeekerId, Long jobId);
}
