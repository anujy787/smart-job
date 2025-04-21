package edu.neu.csye7374.smartjob.model;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "application")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private JobSeeker jobSeeker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_post_id", nullable = false)
    private JobPost jobPost;
    
    /**
     * Application state is stored as a string (e.g., 'APPLIED', 'REJECTED', 'HIRED')
     * in the database. The column type will be VARCHAR.
     */
    @Column(name = "state", columnDefinition = "varchar(20)")
    private String state;

    @Transient
    private edu.neu.csye7374.smartjob.service.state.ApplicationState stateObj;
    private Date applicationDate;
    private Date lastUpdated;

    // Constructors, getters, and setters
    public JobApplication() {}

    public JobApplication(Long id, JobSeeker jobSeeker, JobPost jobPost, String state, Date applicationDate, Date lastUpdated) {
        this.id = id;
        this.jobSeeker = jobSeeker;
        this.jobPost = jobPost;
        this.state = state;
        this.stateObj = getStateObjectFromString(state);
        this.applicationDate = applicationDate;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        this.stateObj = getStateObjectFromString(state);
    }

    public edu.neu.csye7374.smartjob.service.state.ApplicationState getStateObj() {
        if (stateObj == null) {
            stateObj = getStateObjectFromString(state);
        }
        return stateObj;
    }

    public void setStateObj(edu.neu.csye7374.smartjob.service.state.ApplicationState stateObj) {
        this.stateObj = stateObj;
        this.state = stateObj.getStateName();
    }

    private edu.neu.csye7374.smartjob.service.state.ApplicationState getStateObjectFromString(String state) {
        if (state == null) return null;
        switch(state) {
            case "APPLIED":
                return new edu.neu.csye7374.smartjob.service.state.AppliedState();
            case "IN-REVIEW":
                return new edu.neu.csye7374.smartjob.service.state.InReviewState();
            case "HIRED":
                return new edu.neu.csye7374.smartjob.service.state.HiredState();
            case "REJECTED":
                return new edu.neu.csye7374.smartjob.service.state.RejectedState();
            case "WITHDRAWN":
                return new edu.neu.csye7374.smartjob.service.state.WithdrawnState();
            default:
                throw new IllegalArgumentException("Unknown state: " + state);
        }
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
} 