package edu.neu.csye7374.smartjob.service.state;

import edu.neu.csye7374.smartjob.model.JobApplication;

public interface ApplicationState {
    void apply(JobApplication application);
    void withdraw(JobApplication application);
    void reject(JobApplication application);
    void hire(JobApplication application);
    String getStateName();
}
