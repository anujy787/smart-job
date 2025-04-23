package edu.neu.csye7374.smartjob.controller;

import edu.neu.csye7374.smartjob.model.JobSeeker;
import edu.neu.csye7374.smartjob.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/jobseeker")
public class JobSeekerController {

    @Autowired
    private UserService userService;

    @PutMapping("/update")
    public ResponseEntity<?> updateJobSeekerDetails(@RequestBody Map<String, String> updates) {
        try {
            JobSeeker updatedJobSeeker = userService.updateJobSeekerDetails(updates);
            return ResponseEntity.ok(updatedJobSeeker);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 