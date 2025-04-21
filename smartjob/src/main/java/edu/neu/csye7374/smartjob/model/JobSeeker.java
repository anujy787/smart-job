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
    
    /**
     * Builder class for JobSeeker
     */
    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String skills;
        private String experience;
        private String education;
        private String resumeUrl;
        
        public Builder() {
        }
        
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        
        public Builder skills(String skills) {
            this.skills = skills;
            return this;
        }
        
        public Builder experience(String experience) {
            this.experience = experience;
            return this;
        }
        
        public Builder education(String education) {
            this.education = education;
            return this;
        }
        
        public Builder resumeUrl(String resumeUrl) {
            this.resumeUrl = resumeUrl;
            return this;
        }
        
        public JobSeeker build() {
            JobSeeker jobSeeker = new JobSeeker();
            jobSeeker.setFirstName(firstName);
            jobSeeker.setLastName(lastName);
            jobSeeker.setEmail(email);
            jobSeeker.setPassword(password);
            jobSeeker.setSkills(skills);
            jobSeeker.setExperience(experience);
            jobSeeker.setEducation(education);
            jobSeeker.setResumeUrl(resumeUrl);
            return jobSeeker;
        }
    }
} 