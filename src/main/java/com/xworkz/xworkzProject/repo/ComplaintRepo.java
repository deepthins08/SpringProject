package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComplaintRepo {

    boolean save(ComplaintsDTO complaintsDTO);

    Page<ComplaintsDTO> findByUserIdAndStatus(int userId, String status, Pageable pageable);
     ComplaintsDTO findById(int id);

    boolean update(ComplaintsDTO complaintsDTO);

    //for DepartmentAdmin, search by department name
    List<ComplaintsDTO> findByDepartmentName(String departmentName);

    List<ComplaintsDTO> findByUserId(int userId);

    List<ComplaintsDTO> findAll();

    List<ComplaintsDTO> findUnreadNotifications();

    void markNotificationAsRead(int id);

    List<ComplaintsDTO> findUnread();

//    List<ComplaintsDTO> findComplaintsByStatusWithPagination(String status, int pageNumber, int pageSize);

    long countComplaintsByStatus(String status);



}
