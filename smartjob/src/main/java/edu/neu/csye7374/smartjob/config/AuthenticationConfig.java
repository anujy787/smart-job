package edu.neu.csye7374.smartjob.config;

import edu.neu.csye7374.smartjob.repository.UserRepository;
import edu.neu.csye7374.smartjob.service.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfig {
    
    @Autowired
    private UserRepository userRepository;
    
    @Bean
    public AuthenticationManager authenticationManager() {
        // Get the singleton instance
        AuthenticationManager authManager = AuthenticationManager.getInstance();
        
        // Initialize with dependencies
        authManager.initialize(userRepository);
        
        return authManager;
    }
} 