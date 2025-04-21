package edu.neu.csye7374.smartjob.observer;

import edu.neu.csye7374.smartjob.model.Notification;

public interface NotificationObserver {
    void update(Notification notification);
} 