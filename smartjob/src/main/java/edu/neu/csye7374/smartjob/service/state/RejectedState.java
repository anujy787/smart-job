package edu.neu.csye7374.smartjob.service.state;

import edu.neu.csye7374.smartjob.model.JobApplication;
import java.util.Date;

public class RejectedState implements ApplicationState {
    @Override
    public void apply(JobApplication application) {
        // Cannot re-apply if rejected
    }

    @Override
    public void withdraw(JobApplication application) {
        // Already rejected, do nothing
    }

    @Override
    public void reject(JobApplication application) {
        // Already rejected
    }

    @Override
    public void hire(JobApplication application) {
        // Cannot hire a rejected application
    }

    @Override
    public String getStateName() {
        return "REJECTED";
    }
}
