package edu.neu.csye7374.smartjob.service.state;

import edu.neu.csye7374.smartjob.model.JobApplication;
import java.util.Date;

public class AppliedState implements ApplicationState {
    @Override
    public void apply(JobApplication application) {
        // Already applied, do nothing or throw exception
    }

    @Override
    public void withdraw(JobApplication application) {
        application.setStateObj(new WithdrawnState());
        application.setLastUpdated(new Date());
    }

    @Override
    public void reject(JobApplication application) {
        application.setStateObj(new RejectedState());
        application.setState("REJECTED");
        application.setLastUpdated(new Date());
    }

    @Override
    public void hire(JobApplication application) {
        application.setStateObj(new HiredState());
        application.setState("HIRED");
        application.setLastUpdated(new Date());
    }

    public void inReview(JobApplication application) {
        application.setStateObj(new InReviewState());
        application.setLastUpdated(new Date());
    }

    @Override
    public String getStateName() {
        return "APPLIED";
    }
}
