package edu.neu.csye7374.smartjob.config;

import edu.neu.csye7374.smartjob.observer.JobSeekerNotificationObserver;
import edu.neu.csye7374.smartjob.observer.NotificationSubject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {
    
    @Bean
    public NotificationSubject notificationSubject(JobSeekerNotificationObserver observer) {
        NotificationSubject subject = new NotificationSubject();
        subject.attach(observer);
        return subject;
    }
} 