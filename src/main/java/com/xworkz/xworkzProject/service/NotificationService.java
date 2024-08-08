package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.NotificationsDTO;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    void updateAdminNotifications(List<ComplaintsDTO> newComplaints);

    List<NotificationsDTO> getAllNotifications();

    int getUnreadNotificationCount();
    void markAsRead(int id);

    NotificationsDTO findById(int id);

    Optional<NotificationsDTO> findByComplaintId(Integer complaintId);
}
