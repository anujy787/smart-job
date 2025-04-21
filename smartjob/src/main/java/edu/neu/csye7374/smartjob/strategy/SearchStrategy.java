package edu.neu.csye7374.smartjob.strategy;

import java.util.List;

import edu.neu.csye7374.smartjob.model.JobPost;

public interface SearchStrategy {
   
   List<JobPost> search(List<JobPost> jobs, String searchTerm);
}

