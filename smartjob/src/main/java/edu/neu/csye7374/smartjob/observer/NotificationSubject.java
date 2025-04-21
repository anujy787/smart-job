package edu.neu.csye7374.smartjob.observer;

import edu.neu.csye7374.smartjob.model.Notification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationSubject {
    private List<NotificationObserver> observers = new ArrayList<>();

    public void attach(NotificationObserver observer) {
        observers.add(observer);
    }

    public void detach(NotificationObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Notification notification) {
        for (NotificationObserver observer : observers) {
            observer.update(notification);
        }
    }
} 