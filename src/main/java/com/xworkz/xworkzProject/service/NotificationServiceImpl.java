package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.NotificationsDTO;
import com.xworkz.xworkzProject.repo.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationRepo notificationRepo;

    public void updateAdminNotifications(List<ComplaintsDTO> newComplaints) {
        for (ComplaintsDTO complaint : newComplaints) {
            NotificationsDTO notification = new NotificationsDTO();
            notification.setMessage("New complaint from " + complaint.getCreatedBy());
            notification.setCreatedAt(complaint.getCreatedAt());
            notificationRepo.save(notification);
        }
    }

    public List<NotificationsDTO> getAllNotifications() {
        return notificationRepo.findAll();
    }

    public int getUnreadNotificationCount() {
        return notificationRepo.countUnread();
    }

    public void markAsRead(int notificationId) {
        NotificationsDTO notification = notificationRepo.findById(notificationId);
        notification.setRead(true);
        notificationRepo.save(notification);
    }

    @Override
    public NotificationsDTO findById(int id) {
        return notificationRepo.findById(id);
    }

    @Override
    public Optional<NotificationsDTO> findByComplaintId(Integer complaintId) {
      Optional<NotificationsDTO> optional= notificationRepo.findByComplaintId(complaintId);
        return optional;
    }
}

