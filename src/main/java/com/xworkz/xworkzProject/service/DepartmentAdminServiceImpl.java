package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.*;
import com.xworkz.xworkzProject.repo.ComplaintRepo;
import com.xworkz.xworkzProject.repo.DepartmentAdminRepo;
import com.xworkz.xworkzProject.repo.HistoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DepartmentAdminServiceImpl implements DepartmentAdminService{

    @Autowired
   private DepartmentAdminRepo departmentAdminRepo;

    @Autowired
   private ComplaintRepo complaintRepo;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HistoryRepo historyRepo;


    @Override
    public Optional<DepartmentAdminDTO> findAdminByDepartmentId(Integer departmentId) {
       Optional<DepartmentAdminDTO> optional= departmentAdminRepo.findAdminByDepartmentId(departmentId);
       if(optional.isPresent()){
           log.info("admin is found");
           return optional;
       }else{
           log.info("admin is not found");
           return Optional.empty();

       }
    }

    @Override
    public Optional<DepartmentAdminDTO> findByEmail(String email) {

        Optional<DepartmentAdminDTO> optional = departmentAdminRepo.findByEmail(email);
        if (optional.isPresent()) {
            return optional;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean validateAndSave(EmployeeDTO employeeDTO) {
        String generatedPassword = generateRandomPassword();
        String encryptedPassword = passwordEncoder.encode(generatedPassword);
        employeeDTO.setPassword(encryptedPassword);

        sendEmail(employeeDTO, generatedPassword);


        return  departmentAdminRepo.save(employeeDTO);
    }

    @Override
    public List<ComplaintsDTO> getComplaintsByDepartmentId(Integer departmentId) {
        // Add logic to fetch complaints by department ID from the database
        return departmentAdminRepo.findComplaintsByDepartmentId(departmentId);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {

        return departmentAdminRepo.getAllEmployees();
    }

    @Override

    public boolean updateComplaintDepartmentAndStatus(int id, Integer employeeId, String status) {
        departmentAdminRepo.updateComplaintDepartmentAndStatus(id, employeeId, status);
        return true;
    }

    @Override
    public List<ComplaintsDTO> getComplaintsByDepartmentName(String departmentName) {
        return complaintRepo.findByDepartmentName(departmentName);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentAdminRepo.getAllDepartments();
    }

    @Override
    public void sendEmail( EmployeeDTO employeeDTO, String plainPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        employeeDTO.setCreatedAt(LocalDateTime.now());
        employeeDTO.setCreatedBy(employeeDTO.getName());
        message.setTo(employeeDTO.getEmail());
        message.setSubject("Registered Successfully..");
        message.setText("Dear " + employeeDTO.getName() + ",\n\n" +
                "You have been successfully registered.\n\n" +
                "Please sign in to our application: \n\n" +
                "Thanks and Regards,\n" +
                "XworkzProject Team,\n"+
                "© 2024 All Rights Reserved");
        emailSender.send(message);
    }


    public String generateRandomPassword() {
        int length = 6; // desired password length
        String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterSet.length());
            password.append(characterSet.charAt(index));
        }

        return password.toString();
    }


    @Override
    public List<EmployeeDTO> getEmployeesByDepartmentId(Integer departmentId) {
        // Implement the logic to fetch employees by department ID
        if (departmentId == null) {
            return new ArrayList<>();
        }
        List<EmployeeDTO> employees = departmentAdminRepo.findByDepartmentId(departmentId);
        return employees != null ? employees : new ArrayList<>();
    }

    @Override
    public Optional<DepartmentDTO> findByDepartmentName(String departmentName) {
      Optional<DepartmentDTO> list=  departmentAdminRepo.findByDepartmentName(departmentName);
        return list;
    }

    @Override
    public void updateComplaintStatus(int complaintId, String status) {
        ComplaintsDTO complaint = complaintRepo.findById(complaintId);
        if (complaint != null) {
            complaint.setStatus(status); // Update the status
            complaintRepo.update(complaint); // Save the updated complaint

            HistoryDTO history = new HistoryDTO();

            history.setComplaintId(complaintId);
            history.setUserId(complaint.getUserId()); // Assuming the complaint has userId
            history.setStatus(status);
            saveHistory(history);
        }
    }


    @Override
    public void updateComplaintEmployee(int complaintId, Integer employeeId) {
        ComplaintsDTO complaint = complaintRepo.findById(complaintId);
        if (complaint != null) {
            complaint.setEmployeeId(employeeId);
            complaint.setEmployeeRead(false);
            complaintRepo.update(complaint); // Save the updated complaint

//            HistoryDTO history = new HistoryDTO();
//
//            history.setComplaintId(complaintId);
//            history.setUserId(complaint.getUserId()); // Assuming the complaint has userId
//            history.setEmployeeId(employeeId);
//            saveHistory(history);
        }

    }

    @Override
    public void saveHistory(HistoryDTO historyDTO) {
        historyRepo.save(historyDTO);
    }


    @Override
    public void updateLoginCount(int userId) {
        Optional<DepartmentAdminDTO> userOptional = departmentAdminRepo.findById(userId);
        log.info("user Id{} " , userOptional);
        if (userOptional.isPresent()) {
            DepartmentAdminDTO user = userOptional.get();
            log.info("user" + user);
            user.setLoginCount(user.getLoginCount() + 1);
            departmentAdminRepo.merge(user);
        }
    }

    @Override
    public DepartmentAdminDTO savePassword(DepartmentAdminDTO departmentAdminDTO) {
        log.info("save password running");
        String systemUserName = System.getProperty("user.name");
        log.info("System Username:{} " , systemUserName);

        departmentAdminDTO.setModifiedBy(systemUserName); // Set modifiedBy to the same as createdBy
        departmentAdminDTO.setModifiedAt(LocalDateTime.now());
        return departmentAdminRepo.merge(departmentAdminDTO);
    }


    @Override
    public Optional<DepartmentAdminDTO> validateAdminPhone(Long phone) {
        log.info("Running validatePhone in Service");
        long count = departmentAdminRepo.countByPhone(phone);
        if (count >= 1) {
            return Optional.of(new DepartmentAdminDTO());
        }

        return Optional.empty();
    }

    @Override
    public Optional<DepartmentAdminDTO> validateAdminEmail(String email) {
        log.info("Running validateEmail in Service");
        long count = departmentAdminRepo.countByEmail(email);
        if (count >= 1) {
            return Optional.of(new DepartmentAdminDTO());
        }
        return Optional.empty();
    }

    @Override
    public Optional<DepartmentAdminDTO> findByPhone(long phone) {

        return departmentAdminRepo.findByPhone(phone);
    }


    @Override
    public List<ComplaintsDTO> getNotificationsForAdmin(Integer departmentId) {
        return departmentAdminRepo.getNotificationsForAdmin(departmentId);
    }

    @Override
    public boolean markAsRead(int id) {
        departmentAdminRepo.markNotificationAsRead(id);
        return true;
    }

    @Override
    public List<ComplaintsDTO> getUnreadNotifications() {
        return departmentAdminRepo.findUnread();
    }

}
