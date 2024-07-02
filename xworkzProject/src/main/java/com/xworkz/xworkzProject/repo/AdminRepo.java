package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.AdminDTO;
import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;

import java.util.List;
import java.util.Optional;

public interface AdminRepo {

  Optional<AdminDTO> findByEmail(String email);

  List<SignUpDTO> getAllUsers();

  List<ComplaintsDTO> getAllComplaints();



  List<ComplaintsDTO> findComplaintsByTypeOrArea(String type, String area);



  List<ComplaintsDTO> findComplaintsByTypeAndArea(String type, String area);


}
