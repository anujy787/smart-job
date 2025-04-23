package edu.neu.csye7374.smartjob.service.email;

import edu.neu.csye7374.smartjob.model.JobApplication;
import edu.neu.csye7374.smartjob.model.JobPost;
import edu.neu.csye7374.smartjob.model.JobSeeker;
import edu.neu.csye7374.smartjob.service.EmailService;

public class JobApplicationEmailAdapter {
    private final EmailService emailService;

    public JobApplicationEmailAdapter(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendApplicationStatusEmail(JobApplication application, String status) {
        JobSeeker seeker = application.getJobSeeker();
        JobPost job = application.getJobPost();
        String subject;
        String templatePath;
        switch (status) {
            case "HIRED":
                subject = "Congratulations! You've been hired";
                templatePath = "/templates/hired_notification.html";
                break;
            case "REJECTED":
                subject = "Update on your job application";
                templatePath = "/templates/rejected_notification.html";
                break;
            case "APPLIED":
                subject = "Your application has been received";
                templatePath = "/templates/applied_notification.html";
                break;
            case "WITHDRAWN":
                subject = "Application Withdrawn";
                templatePath = "/templates/withdrawn_notification.html";
                break;
            default:
                subject = "Update on your job application";
                templatePath = "/templates/applied_notification.html";
        }
        String html = emailService.loadTemplate(templatePath)
            .replace("{{firstName}}", seeker.getFirstName())
            .replace("{{jobTitle}}", job.getTitle())
            .replace("{{companyName}}", job.getCompanyName());
        emailService.sendCustomEmail(seeker.getEmail(), subject, html);
    }
}
