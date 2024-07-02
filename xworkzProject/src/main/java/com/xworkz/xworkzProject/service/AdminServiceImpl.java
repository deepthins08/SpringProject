package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.AdminDTO;
import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.repo.AdminRepo;
import com.xworkz.xworkzProject.repo.ComplaintRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ComplaintRepo complaintRepo;

    @Override
    public Optional<AdminDTO> findByEmail(String email) {

        Optional<AdminDTO> optional = adminRepo.findByEmail(email);
        if (optional.isPresent()) {
            return optional;
        } else {
            return Optional.empty();
        }
    }


    @Override
    public List<SignUpDTO> getAllUsers() {
        return adminRepo.getAllUsers();
    }

    @Override
    public List<ComplaintsDTO> getAllComplaints() {
        return adminRepo.getAllComplaints();
    }


    @Override
    public List<ComplaintsDTO> searchComplaintsByTypeOrArea(String type, String area) {
        List<ComplaintsDTO> list=adminRepo.findComplaintsByTypeOrArea(type,area);
        if(!list.isEmpty()){
            System.out.println("List found");
            return list;
        }else {
            return Collections.emptyList();
        }
    }

    @Override
    public void updateComplaintStatus(int complaintId, String status) {
        ComplaintsDTO complaint = complaintRepo.findById(complaintId);
        if (complaint != null) {
            complaint.setStatus(status); // Update the status
            complaintRepo.update(complaint); // Save the updated complaint
        }
    }


    @Override
    public List<ComplaintsDTO> searchComplaintsByTypeAndArea(String type, String area) {
        List<ComplaintsDTO> list= adminRepo.findComplaintsByTypeAndArea(type, area);
        if(!list.isEmpty()){
            System.out.println("List found");
            return list;
        }else {
            return Collections.emptyList();
        }
    }


}


