package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Optional<EmployeeDTO> findByEmail(String email);

    Optional<EmployeeDTO> findByPhone(long phone);

    List<ComplaintsDTO> getAssignedComplaints(int employeeId);

    void updateComplaintStatus(int complaintId, String status);

    Optional<EmployeeDTO> validatePhone(Long phone);

    Optional<EmployeeDTO> validateEmail(String email);

    String generateAndSendOtp(EmployeeDTO employee);



    void sendOtpEmail(EmployeeDTO employee, String otp);


}
