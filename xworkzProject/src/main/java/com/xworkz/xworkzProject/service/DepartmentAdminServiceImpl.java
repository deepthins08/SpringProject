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
    public List<ComplaintsDTO> getAllComplaints() {
        // Implement fetching complaints logic from repository
        return departmentAdminRepo.getAllComplaints();
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
        message.setTo(employeeDTO.getEmail());
        message.setSubject("Complaint Received");
        message.setText("Dear " + employeeDTO.getName() + ",\n\n" +
                "You have been successfully registered.\n\n" +
                "Please sign in using this password: " + plainPassword + "\n\n" +
                "Thanks and Regards,\n" +
                "XworkzProject Team");
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
            complaintRepo.update(complaint); // Save the updated complaint

            HistoryDTO history = new HistoryDTO();

            history.setComplaintId(complaintId);
            history.setUserId(complaint.getUserId()); // Assuming the complaint has userId
            history.setEmployeeId(employeeId);
            saveHistory(history);
        }

    }

    @Override
    public void saveHistory(HistoryDTO historyDTO) {
        historyRepo.save(historyDTO);
    }
}
