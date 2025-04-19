package edu.neu.csye7374.smartjob.controller;

import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String showLandingPage() {
        return "landing";
    }
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    
    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
    
    @PostMapping("/signup")
    public String processSignup(@ModelAttribute User user) {
        try {
            userService.registerUser(user);
            return "redirect:/login?signupSuccess=true";
        } catch (RuntimeException e) {
            return "redirect:/signup?error=" + e.getMessage();
        }
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