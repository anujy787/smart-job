package edu.neu.csye7374.smartjob.strategy;

import java.util.List;
import java.util.stream.Collectors;
import edu.neu.csye7374.smartjob.model.JobApplication;

public class ApplicationLocationSearchStrategy implements SearchStrategy<JobApplication> {
    @Override
    public List<JobApplication> search(List<JobApplication> apps, String searchTerm) {
        String term = searchTerm.toLowerCase();
        return apps.stream()
                .filter(app -> app.getJobPost().getLocation()
                        .toLowerCase()
                        .contains(term))
                .collect(Collectors.toList());
    }
}