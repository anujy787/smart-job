package edu.neu.csye7374.smartjob.service;

import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

import java.util.Base64;
import java.util.Optional;

/**
 * Singleton authentication manager to handle all authentication related operations
 */
public class AuthenticationManager {
    
    // The singleton instance
    private static AuthenticationManager instance;
    
    // Dependencies
    private UserRepository userRepository;
    
    // Private constructor to prevent instantiation from outside
    private AuthenticationManager() {
        // Private constructor to enforce singleton pattern
    }
    
    /**
     * Get the singleton instance of AuthenticationManager
     * @return the AuthenticationManager instance
     */
    public static synchronized AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }
    
    /**
     * Initialize with required dependencies
     * @param userRepository the user repository
     */
    public void initialize(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Authenticate a user by email and password
     * @param email the user's email
     * @param password the user's password
     * @return the authenticated User if successful
     * @throws RuntimeException if authentication fails
     */
    public User authenticate(String email, String password) {
        if (userRepository == null) {
            throw new IllegalStateException("AuthenticationManager not initialized with dependencies");
        }
        
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
    
    /**
     * Log in a user and store in the session
     * @param email the user's email
     * @param password the user's password
     * @param session the HTTP session to store the user
     * @return the authenticated User
     */
    public User login(String email, String password, HttpSession session) {
        User user = authenticate(email, password);
        session.setAttribute("user", user);
        return user;
    }
    
    /**
     * Check if a user is logged in
     * @param session the HTTP session
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }
    
    /**
     * Get the current logged in user
     * @param session the HTTP session
     * @return the logged in User or null if not logged in
     */
    public User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }
    
    /**
     * Log out the current user
     * @param session the HTTP session
     */
    public void logout(HttpSession session) {
        session.invalidate();
    }
} 