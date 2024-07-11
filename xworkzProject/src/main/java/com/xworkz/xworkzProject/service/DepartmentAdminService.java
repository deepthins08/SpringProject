package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.DepartmentAdminDTO;
import com.xworkz.xworkzProject.dto.DepartmentDTO;
import com.xworkz.xworkzProject.dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface DepartmentAdminService {

    Optional<DepartmentAdminDTO> findByEmail(String email);

    boolean validateAndSave(EmployeeDTO employeeDTO);

    List<ComplaintsDTO> getAllComplaints();

    List<EmployeeDTO> getAllEmployees();

    List<ComplaintsDTO> getComplaintsByDepartmentName(String departmentName);

    boolean updateComplaintDepartmentAndStatus(Long id, Long departmentId, String status);

    List<DepartmentDTO> getAllDepartments();

    void sendEmail(EmployeeDTO employeeDTO, String plainPassword);

    List<EmployeeDTO> getEmployeesByDepartmentId(Integer departmentId);
}
