package edu.neu.csye7374.smartjob.strategy;

import java.util.List;
import java.util.stream.Collectors;

import edu.neu.csye7374.smartjob.model.JobPost;


public class LocationSearchStrategy implements SearchStrategy <JobPost>{

    @Override
    public List<JobPost> search(List<JobPost> jobs, String searchTerm) {
        return jobs.stream()
                .filter(job -> job.getLocation().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }
}
