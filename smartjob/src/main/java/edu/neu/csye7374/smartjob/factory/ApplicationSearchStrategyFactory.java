package edu.neu.csye7374.smartjob.factory;

import edu.neu.csye7374.smartjob.model.JobApplication;
import edu.neu.csye7374.smartjob.strategy.ApplicationCompanySearchStrategy;
import edu.neu.csye7374.smartjob.strategy.ApplicationLocationSearchStrategy;
import edu.neu.csye7374.smartjob.strategy.ApplicationTitleSearchStrategy;
import edu.neu.csye7374.smartjob.strategy.SearchStrategy;

public class ApplicationSearchStrategyFactory {

    /**
     * @param searchType one of: "title", "company", "location"
     * @return the corresponding SearchStrategy<JobApplication>
     */
    public static SearchStrategy<JobApplication> getStrategy(String searchType) {
        return switch (searchType.toLowerCase()) {
            case "title" -> new ApplicationTitleSearchStrategy();
            case "company" -> new ApplicationCompanySearchStrategy();
            case "location" -> new ApplicationLocationSearchStrategy();
            default -> throw new IllegalArgumentException("Invalid search type: " + searchType);
        };
    }
}