package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.*;

import java.util.List;
import java.util.Optional;

public interface DepartmentAdminRepo {

    Optional<DepartmentAdminDTO> findByEmail(String email);

    boolean save(EmployeeDTO employeeDTO);

    List<ComplaintsDTO> getAllComplaints();

    List<EmployeeDTO> getAllEmployees();

    void updateComplaintDepartmentAndStatus(int id, Integer employeeId, String status);

    List<DepartmentDTO> getAllDepartments();

    List<EmployeeDTO> findByDepartmentId(int departmentId);

    Optional<DepartmentDTO> findByDepartmentName(String departmentName);
}
