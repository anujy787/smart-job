package edu.neu.csye7374.smartjob.service;

import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }
    
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        return user;
    }
} 