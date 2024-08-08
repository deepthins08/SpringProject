package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AdminService {

    Optional<AdminDTO> findByEmail(String email);

    List<SignUpDTO> getAllUsers();

    List<ComplaintsDTO> getAllComplaints();

    List<ComplaintsDTO> searchComplaintsByTypeOrArea(String type, String area);


    List<ComplaintsDTO> searchComplaintsByTypeAndArea(String type, String area);

    void updateComplaintStatus(int complaintId, String status);

    void updateComplaintDepartment(int complaintId, int departmentId);

    int getDepartmentIdByType(String departmentName);

    void saveHistory(HistoryDTO historyDTO);

    boolean validateAndSaveDepartmentAdmin(DepartmentAdminDTO departmentAdminDTO);

    void sendEmail( DepartmentAdminDTO departmentAdminDTO, String plainPassword);

    ComplaintsDTO getComplaintById(int id);


}
