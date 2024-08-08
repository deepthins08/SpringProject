package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.EmployeeDTO;
import com.xworkz.xworkzProject.dto.HistoryDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Optional<EmployeeDTO> findByEmail(String email);

    Optional<EmployeeDTO> findByPhone(long phone);

    List<ComplaintsDTO> getAssignedComplaints(int employeeId);

    void updateComplaintStatus(int complaintId, String status, String comment);

    boolean verifyOtp(int complaintId, String otp);

    void saveResolvedComment(int complaintId, String comment);

    Optional<EmployeeDTO> validatePhone(Long phone);

    Optional<EmployeeDTO> validateEmail(String email);

    String generateAndSendOtp(EmployeeDTO employee);

    void sendOtpToUser(int complaintId);

    boolean verifyUserOtp(int complaintId, String otp);


    void sendOtpToUserEmail(int userId, String otp);
    void sendOtpEmail(EmployeeDTO employee, String otp);

    void saveHistory(HistoryDTO historyDTO);

    void deleteExpiredOtps();

    boolean markAsRead(int id);

    List<ComplaintsDTO> getUnreadNotifications();



}
