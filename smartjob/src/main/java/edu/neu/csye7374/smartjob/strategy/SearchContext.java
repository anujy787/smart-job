package edu.neu.csye7374.smartjob.strategy;

import java.util.List;
import edu.neu.csye7374.smartjob.model.JobPost;

public class SearchContext {

    private SearchStrategy searchStrategy;

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }   

    public List<JobPost> executeSearch(List<JobPost> jobs, String searchTerm) {
        if (searchStrategy == null) {
            throw new IllegalStateException("Search strategy not set");
        }
        return searchStrategy.search(jobs, searchTerm);
    }
    
}
