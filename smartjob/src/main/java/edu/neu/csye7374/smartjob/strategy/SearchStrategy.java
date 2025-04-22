package edu.neu.csye7374.smartjob.strategy;

import java.util.List;

import edu.neu.csye7374.smartjob.model.JobPost;

public interface SearchStrategy<T> {
   List<T> search(List<T> items, String searchTerm);
}