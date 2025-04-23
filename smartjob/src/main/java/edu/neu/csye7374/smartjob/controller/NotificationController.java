package edu.neu.csye7374.smartjob.controller;

import edu.neu.csye7374.smartjob.model.Notification;
import edu.neu.csye7374.smartjob.model.User;
import edu.neu.csye7374.smartjob.model.UserRole;
import edu.neu.csye7374.smartjob.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public String getNotifications(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.JOB_SEEKER) {
            return "redirect:/dashboard";
        }
        
        List<Notification> notifications = notificationService.getUserNotifications(user);
        model.addAttribute("notifications", notifications);
        return "notifications";
    }

    @PostMapping("/mark-read/{id}")
    @ResponseBody
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mark-all-read")
    @ResponseBody
    public ResponseEntity<?> markAllAsRead(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            notificationService.markAllAsRead(user);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unread-count")
    @ResponseBody
    public ResponseEntity<Long> getUnreadCount(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok(0L);
        }
        return ResponseEntity.ok(notificationService.getUnreadNotificationCount(user));
    }

    @PostMapping("/delete-read")
    @ResponseBody
    public ResponseEntity<?> deleteReadNotifications(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            notificationService.deleteReadNotifications(user);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok().build();
    }

} 