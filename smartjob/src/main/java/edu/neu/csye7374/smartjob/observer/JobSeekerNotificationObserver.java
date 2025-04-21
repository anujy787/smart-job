package edu.neu.csye7374.smartjob.observer;

import edu.neu.csye7374.smartjob.model.Notification;
import edu.neu.csye7374.smartjob.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobSeekerNotificationObserver implements NotificationObserver {
    
    @Autowired
    private NotificationService notificationService;

    @Override
    public void update(Notification notification) {
        notificationService.saveNotification(notification);
    }
} 