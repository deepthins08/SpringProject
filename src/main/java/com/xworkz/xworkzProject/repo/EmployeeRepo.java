package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo {
    Optional<EmployeeDTO> findByEmail(String email);

    List<ComplaintsDTO> findAssignedComplaintsByEmployeeId(int employeeId);

    void updateComplaintStatus(int id, String status);

    Optional<EmployeeDTO> findByPhone(Long phone);

    long countByEmail(String email);

    long countByPhone(Long phone);

    void save(EmployeeDTO employeeDTO);

    void updateOtp(String email, String password);

    List<EmployeeDTO> findAll();

    void markNotificationAsRead(int id);

    List<ComplaintsDTO> findUnread();

}
