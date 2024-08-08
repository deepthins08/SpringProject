package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.NotificationsDTO;

import javax.management.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationRepo {

    void save(NotificationsDTO notification);

    List<NotificationsDTO> findAll();

    int countUnread();

    void markNotificationAsRead(int id);

    NotificationsDTO findById(int id);

    Optional<NotificationsDTO> findByComplaintId(Integer complaintId);

}
