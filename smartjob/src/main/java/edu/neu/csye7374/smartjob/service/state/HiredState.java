package edu.neu.csye7374.smartjob.service.state;

import edu.neu.csye7374.smartjob.model.JobApplication;
import java.util.Date;

public class HiredState implements ApplicationState {
    @Override
    public void apply(JobApplication application) {
        // Already hired, do nothing
    }

    @Override
    public void withdraw(JobApplication application) {
        // Cannot withdraw if hired
    }

    @Override
    public void reject(JobApplication application) {
        // Cannot reject if already hired
    }

    @Override
    public void hire(JobApplication application) {
        // Already hired
    }

    @Override
    public String getStateName() {
        return "HIRED";
    }
}
