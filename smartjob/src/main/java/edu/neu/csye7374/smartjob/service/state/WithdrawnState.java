package edu.neu.csye7374.smartjob.service.state;

import edu.neu.csye7374.smartjob.model.JobApplication;
import java.util.Date;

public class WithdrawnState implements ApplicationState {
    @Override
    public void apply(JobApplication application) {
        // Cannot re-apply if withdrawn
    }

    @Override
    public void withdraw(JobApplication application) {
        // Already withdrawn
    }

    @Override
    public void reject(JobApplication application) {
        // Cannot reject withdrawn
    }

    @Override
    public void hire(JobApplication application) {
        // Cannot hire withdrawn
    }

    @Override
    public String getStateName() {
        return "WITHDRAWN";
    }
}
