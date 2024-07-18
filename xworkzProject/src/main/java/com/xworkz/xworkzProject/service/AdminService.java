package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.AdminDTO;
import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.HistoryDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;

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

}
