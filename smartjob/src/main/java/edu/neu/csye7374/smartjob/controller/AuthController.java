package edu.neu.csye7374.smartjob.controller;

import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String showLandingPage() {
        return "index";
    }
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    
    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";
    }
    
    @PostMapping("/signup")
    public String processSignup(@RequestParam String email, 
                                @RequestParam String password,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String role,
                                HttpSession session) {
        return "forward:/signup/process";
    }
    
    @PostMapping("/login")
    public String processLogin(@RequestParam String email, 
                             @RequestParam String password,
                             HttpSession session) {
        try {
            User user = userService.authenticateUser(email, password);
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } catch (RuntimeException e) {
            return "redirect:/login?error=" + e.getMessage();
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
} 