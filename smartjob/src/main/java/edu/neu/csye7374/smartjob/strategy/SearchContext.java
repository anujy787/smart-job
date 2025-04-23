package edu.neu.csye7374.smartjob.strategy;

import java.util.List;
import edu.neu.csye7374.smartjob.model.JobPost;

public class SearchContext<T> {

    private SearchStrategy<T> searchStrategy;

    public void setSearchStrategy(SearchStrategy<T> strategy) {
        this.searchStrategy = strategy;
    }

    public List<T> executeSearch(List<T> items, String searchTerm) {
        if (searchStrategy == null) {
            throw new IllegalStateException("Search strategy not set");
        }
        return searchStrategy.search(items, searchTerm);
    }
}