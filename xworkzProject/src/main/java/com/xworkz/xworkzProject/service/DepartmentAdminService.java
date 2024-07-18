package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.*;

import java.util.List;
import java.util.Optional;

public interface DepartmentAdminService {

    Optional<DepartmentAdminDTO> findByEmail(String email);

    boolean validateAndSave(EmployeeDTO employeeDTO);

    List<ComplaintsDTO> getAllComplaints();

    List<EmployeeDTO> getAllEmployees();

    List<ComplaintsDTO> getComplaintsByDepartmentName(String departmentName);

    boolean updateComplaintDepartmentAndStatus(int id, Integer employeeId, String status);

    List<DepartmentDTO> getAllDepartments();

    void sendEmail(EmployeeDTO employeeDTO, String plainPassword);

    List<EmployeeDTO> getEmployeesByDepartmentId(Integer departmentId);

    Optional<DepartmentDTO> findByDepartmentName(String departmentName);

    void updateComplaintStatus(int complaintId, String status);

    void updateComplaintEmployee(int complaintId, Integer employeeId);

    void saveHistory(HistoryDTO historyDTO);

}
