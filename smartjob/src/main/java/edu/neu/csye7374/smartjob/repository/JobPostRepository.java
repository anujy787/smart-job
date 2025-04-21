package edu.neu.csye7374.smartjob.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.model.User;

public interface JobPostRepository extends JpaRepository<JobPost,Long> {
	List<JobPost> findByUserOrderByPostedDateDesc(User user);
}
