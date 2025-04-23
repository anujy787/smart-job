package edu.neu.csye7374.smartjob.service;

import edu.neu.csye7374.smartjob.factory.UserFactory;
import edu.neu.csye7374.smartjob.model.Employer;
import edu.neu.csye7374.smartjob.model.JobSeeker;
import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.repository.UserRepository;
import edu.neu.csye7374.smartjob.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Base64;
import java.util.Map;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserFactory userFactory;
    
    @Autowired
    private EmailService emailService;
    
    /**
     * Register a new JobSeeker
     */
    public JobSeeker registerJobSeeker(String firstName, String lastName, String email, String password, 
                                      String skills, String experience, String education) {
        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
        
        // Encode password (simple encoding for demo purposes)
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        
        // Create JobSeeker using factory
        JobSeeker jobSeeker = userFactory.createJobSeeker(firstName, lastName, email, encodedPassword,
                                                          skills, experience, education);
        
        // Save to database
        JobSeeker savedJobSeeker = (JobSeeker) userRepository.save(jobSeeker);
        
        // Send welcome email
        emailService.sendWelcomeEmail(email, firstName, false);
        
        return savedJobSeeker;
    }
    
    /**
     * Register a new Employer
     */
    public Employer registerEmployer(String firstName, String lastName, String email, String password,
                                     String companyName, String industry, String companySize) {
        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
        
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        
        Employer employer = userFactory.createEmployer(firstName, lastName, email, encodedPassword,
                                                       companyName, industry, companySize);
        
        Employer savedEmployer = (Employer) userRepository.save(employer);
        
        // Send welcome email
        emailService.sendWelcomeEmail(email, firstName, true);
        
        return savedEmployer;
    }
    

    public User authenticateUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }
        
        User user = userOpt.get();
        
        String encodedInput = Base64.getEncoder().encodeToString(password.getBytes());
        if (!encodedInput.equals(user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        
        return user;
    }

    public JobSeeker updateJobSeekerDetails(Map<String, String> updates) {
        Long userId = Long.parseLong(updates.get("userId"));
        JobSeeker jobSeeker = (JobSeeker) userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Job seeker not found"));

        System.out.println("Updating job seeker with ID: " + userId);
        System.out.println("Current values - FirstName: " + jobSeeker.getFirstName() + 
                         ", LastName: " + jobSeeker.getLastName() +
                         ", Skills: " + jobSeeker.getSkills() +
                         ", Experience: " + jobSeeker.getExperience() +
                         ", Education: " + jobSeeker.getEducation());

        if (updates.containsKey("firstName")) {
            jobSeeker.setFirstName(updates.get("firstName"));
        }
        if (updates.containsKey("lastName")) {
            jobSeeker.setLastName(updates.get("lastName"));
        }
        if (updates.containsKey("skills")) {
            jobSeeker.setSkills(updates.get("skills"));
        }
        if (updates.containsKey("experience")) {
            jobSeeker.setExperience(updates.get("experience"));
        }
        if (updates.containsKey("education")) {
            jobSeeker.setEducation(updates.get("education"));
        }

        JobSeeker updatedJobSeeker = (JobSeeker) userRepository.save(jobSeeker);
        System.out.println("Updated values - FirstName: " + updatedJobSeeker.getFirstName() + 
                         ", LastName: " + updatedJobSeeker.getLastName() +
                         ", Skills: " + updatedJobSeeker.getSkills() +
                         ", Experience: " + updatedJobSeeker.getExperience() +
                         ", Education: " + updatedJobSeeker.getEducation());

        return updatedJobSeeker;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
} 