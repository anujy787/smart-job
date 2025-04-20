package edu.neu.csye7374.smartjob.factory;

import edu.neu.csye7374.smartjob.model.*;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    
    /**
     * Creates a user of the specified role type
     * 
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param email the user's email
     * @param password the user's password
     * @param role the user's role
     * @return a User instance of the appropriate type
     */
    public User createUser(String firstName, String lastName, String email, String password, UserRole role) {
        User user;
        
        // Dynamically create the appropriate user type based on role
        switch (role) {
            case JOB_SEEKER:
                user = new JobSeeker();
                break;
            case EMPLOYER:
                user = new Employer();
                break;
            default:
                throw new IllegalArgumentException("Unsupported user role: " + role);
        }
        
        // Set common properties
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        
        return user;
    }
    
    public JobSeeker createJobSeeker(String firstName, String lastName, String email, String password, 
                                    String skills, String experience, String education) {
        JobSeeker jobSeeker = (JobSeeker) createUser(firstName, lastName, email, password, UserRole.JOB_SEEKER);
        jobSeeker.setSkills(skills);
        jobSeeker.setExperience(experience);
        jobSeeker.setEducation(education);
        return jobSeeker;
    }
    
    public Employer createEmployer(String firstName, String lastName, String email, String password,
                                  String companyName, String industry, String companySize) {
        Employer employer = (Employer) createUser(firstName, lastName, email, password, UserRole.EMPLOYER);
        employer.setCompanyName(companyName);
        employer.setIndustry(industry);
        employer.setCompanySize(companySize);
        return employer;
    }
} 