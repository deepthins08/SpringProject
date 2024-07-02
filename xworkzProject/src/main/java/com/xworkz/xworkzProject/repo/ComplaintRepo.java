package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;

import java.util.List;

public interface ComplaintRepo {

    boolean save(ComplaintsDTO complaintsDTO);

    List<ComplaintsDTO> findByUserIdAndStatus(int userId, String status);

     ComplaintsDTO findById(int id);

    boolean update(ComplaintsDTO complaintsDTO);


}
