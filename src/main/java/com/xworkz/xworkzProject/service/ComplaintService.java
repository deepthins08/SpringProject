package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ComplaintService {

    boolean validateAndSave(ComplaintsDTO complaintsDTO);

    List<ComplaintsDTO> getComplaintsByUserIdAndStatus(int userId, String status);

    void sendEmail(SignUpDTO signUpDTO,ComplaintsDTO complaintsDTO);

    public ComplaintsDTO findById(int id);

    boolean validateAndUpdate(ComplaintsDTO complaintsDTO);

    List<ComplaintsDTO> getComplaintsByUserId(int userId);

    int getUnreadNotificationCount();

    boolean markAsRead(int notificationId);

    List<ComplaintsDTO> getAllComplaints();

    List<ComplaintsDTO> getUnreadNotifications();


    Date convertToDateViaInstant(LocalDateTime dateToConvert);

    String formatNotificationDate(Date date);

}
