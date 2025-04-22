package edu.neu.csye7374.smartjob.repository;

import edu.neu.csye7374.smartjob.model.Notification;
import edu.neu.csye7374.smartjob.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
    List<Notification> findByUserAndIsReadFalseOrderByCreatedAtDesc(User user);
    long countByUserAndIsReadFalse(User user);

    void deleteByUserAndIsReadTrue(User user);
} 