package edu.neu.csye7374.smartjob.template;

import edu.neu.csye7374.smartjob.model.JobSeeker;
import java.util.HashMap;
import java.util.Map;

public class JobSeekerProfileTemplate implements ProfileTemplate {
    private final JobSeeker jobSeeker;

    public JobSeekerProfileTemplate(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    @Override
    public Map<String, Object> getProfileData() {
        Map<String, Object> profileData = new HashMap<>();
        profileData.put("firstName", jobSeeker.getFirstName());
        profileData.put("lastName", jobSeeker.getLastName());
        profileData.put("email", jobSeeker.getEmail());
        profileData.put("skills", jobSeeker.getSkills());
        profileData.put("experience", jobSeeker.getExperience());
        profileData.put("education", jobSeeker.getEducation());
        profileData.put("resumeUrl", jobSeeker.getResumeUrl());
        return profileData;
    }

    @Override
    public String getProfileView() {
        return "jobseeker-profile";
    }
} 