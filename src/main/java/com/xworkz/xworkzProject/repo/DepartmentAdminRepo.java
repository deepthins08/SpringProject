package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.*;

import java.util.List;
import java.util.Optional;

public interface DepartmentAdminRepo {

    Optional<DepartmentAdminDTO> findAdminByDepartmentId(Integer departmentId);

    Optional<DepartmentAdminDTO> findByEmail(String email);

    boolean save(EmployeeDTO employeeDTO);

    List<ComplaintsDTO> findComplaintsByDepartmentId(Integer departmentId);
    List<EmployeeDTO> getAllEmployees();

    void updateComplaintDepartmentAndStatus(int id, Integer employeeId, String status);

    List<DepartmentDTO> getAllDepartments();

    List<EmployeeDTO> findByDepartmentId(int departmentId);

    Optional<DepartmentDTO> findByDepartmentName(String departmentName);

    Optional<DepartmentAdminDTO> findById(int id);

    DepartmentAdminDTO merge(DepartmentAdminDTO departmentAdminDTO);

    long countByEmail(String email);

    long countByPhone(Long phone);

    Optional<DepartmentAdminDTO> findByPhone(Long phone);

    List<ComplaintsDTO> getNotificationsForAdmin(Integer departmentId);

    void markNotificationAsRead(int id);

    List<ComplaintsDTO> findUnread();
}
