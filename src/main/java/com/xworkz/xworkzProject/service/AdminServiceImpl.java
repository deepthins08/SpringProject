package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.*;
import com.xworkz.xworkzProject.repo.AdminRepo;
import com.xworkz.xworkzProject.repo.ComplaintRepo;
import com.xworkz.xworkzProject.repo.HistoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private HistoryRepo historyRepo;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
            log.info("List found");
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
            complaint.setModifiedAt(LocalDateTime.now());
            String systemUserName = System.getProperty("user.name");
            log.info("System Username:{} " , systemUserName);
            complaint.setModifiedBy(System.getProperty(systemUserName));
            complaintRepo.update(complaint); // Save the updated complaint

            HistoryDTO history = new HistoryDTO();


            history.setComplaintId(complaintId);
            history.setUserId(complaint.getUserId()); // Assuming the complaint has userId
            history.setStatus(status);
            saveHistory(history);
        }
    }


    @Override
    public void updateComplaintDepartment(int complaintId, int departmentId) {
        ComplaintsDTO complaint = complaintRepo.findById(complaintId);
        if (complaint != null) {
            complaint.setDepartmentId(departmentId);
            complaint.setModifiedAt(LocalDateTime.now());
            String systemUserName = System.getProperty("user.name");
            log.info("System Username:{} " , systemUserName);
            complaint.setModifiedBy(System.getProperty(systemUserName));
            complaintRepo.update(complaint); // Save the updated complaint


            HistoryDTO history = new HistoryDTO();

            history.setComplaintId(complaintId);
            history.setUserId(complaint.getUserId()); // Assuming the complaint has userId
            history.setDepartmentId(departmentId);
            saveHistory(history);
        }
    }

    @Override
    public void saveHistory(HistoryDTO historyDTO) {
        historyRepo.save(historyDTO);
    }

    @Override
    public boolean validateAndSaveDepartmentAdmin(DepartmentAdminDTO departmentAdminDTO) {
        String generatedPassword=generateRandomPassword();
        String encryptedPassword=passwordEncoder.encode(generatedPassword);
        departmentAdminDTO.setPassword(encryptedPassword);
        sendEmail(departmentAdminDTO,generatedPassword);
        departmentAdminDTO.setCreatedAt(LocalDateTime.now());
        departmentAdminDTO.setCreatedBy(departmentAdminDTO.getName());
        departmentAdminDTO.setLoginCount(0);
       boolean validate= adminRepo.saveDepartmentAdmin(departmentAdminDTO);
       if(validate){

           log.info("Department Admin data is saved successfully");
       }else{
           log.info("Department Admin data is not saved");
       }
        return validate;
    }


    @Override
    public List<ComplaintsDTO> searchComplaintsByTypeAndArea(String type, String area) {
        List<ComplaintsDTO> list= adminRepo.findComplaintsByTypeAndArea(type, area);
        if(!list.isEmpty()){
            log.info("List found");
            return list;
        }else {
            return Collections.emptyList();
        }
    }


    public int getDepartmentIdByType(String departmentName) {
        return adminRepo.getDepartmentIdByType(departmentName); // Assuming adminRepository handles database operations
    }


    @Override
    public void sendEmail( DepartmentAdminDTO departmentAdminDTO, String plainPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(departmentAdminDTO.getEmail());
        message.setSubject("Complaint Received");
        message.setText("Dear " + departmentAdminDTO.getName() + ",\n\n" +
                "You have been successfully registered.\n\n" +
                "Please sign in using this password: " + plainPassword + "\n\n" +
                "Thanks and Regards,\n" +
                "XworkzProject Team");
        emailSender.send(message);
    }

    @Override
    public ComplaintsDTO getComplaintById(int id) {
        return adminRepo.getComplaintById(id);
    }


    public String generateRandomPassword() {
        int length = 16; // desired password length
        String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterSet.length());
            password.append(characterSet.charAt(index));
        }

        return password.toString();
    }




}


