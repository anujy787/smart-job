package edu.neu.csye7374.smartjob.factory;

import edu.neu.csye7374.smartjob.strategy.*;

public class SearchStrategyFactory {
    public static SearchStrategy getStrategy(String searchType) {
        return switch (searchType.toLowerCase()) {
            case "title" -> new TitleSearchStrategy();
            case "company" -> new CompanySearchStrategy();
            case "location" -> new LocationSearchStrategy();
            default -> throw new IllegalArgumentException("Invalid search type: " + searchType);
        };
    }
}