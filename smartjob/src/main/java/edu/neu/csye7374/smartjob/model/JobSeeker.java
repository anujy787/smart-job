package edu.neu.csye7374.smartjob.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("JOB_SEEKER")
public class JobSeeker extends User {
    
    @Column
    private String skills;
    
    @Column
    private String experience;
    
    @Column
    private String education;
    
    @Column
    private String resumeUrl;

    public JobSeeker() {
        super();
        setRole(UserRole.JOB_SEEKER);
    }
    
    @Override
    public String getUserType() {
        return "JOB_SEEKER";
    }

    // Getters and Setters for JobSeeker specific fields
    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
} 