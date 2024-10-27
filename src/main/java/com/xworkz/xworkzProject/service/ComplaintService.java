package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ComplaintService {

    boolean validateAndSave(ComplaintsDTO complaintsDTO);

    Page<ComplaintsDTO> getComplaintsByUserIdAndStatus(int userId, String status,int page, int pageSize);

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

//    Page<ComplaintsDTO> getComplaintsByStatusWithPagination(String status, int pageNumber, int pageSize);

}
