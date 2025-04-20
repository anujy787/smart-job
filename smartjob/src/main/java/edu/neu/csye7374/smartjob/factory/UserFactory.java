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
                user = new JobSeeker.Builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .password(password)
                    .build();
                break;
            case EMPLOYER:
                user = new Employer.Builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .password(password)
                    .build();
                break;
            default:
                throw new IllegalArgumentException("Unsupported user role: " + role);
        }
        
        return user;
    }
    
    public JobSeeker createJobSeeker(String firstName, String lastName, String email, String password, 
                                    String skills, String experience, String education) {
        return new JobSeeker.Builder()
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .password(password)
            .skills(skills)
            .experience(experience)
            .education(education)
            .build();
    }
    
    public Employer createEmployer(String firstName, String lastName, String email, String password,
                                  String companyName, String industry, String companySize) {
        return new Employer.Builder()
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .password(password)
            .companyName(companyName)
            .industry(industry)
            .companySize(companySize)
            .build();
    }
} 