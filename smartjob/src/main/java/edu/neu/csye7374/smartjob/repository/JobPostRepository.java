package edu.neu.csye7374.smartjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.neu.csye7374.smartjob.model.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost,Long> {

}
