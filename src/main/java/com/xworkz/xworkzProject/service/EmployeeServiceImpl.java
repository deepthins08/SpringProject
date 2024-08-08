package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.EmployeeDTO;
import com.xworkz.xworkzProject.dto.HistoryDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.repo.ComplaintRepo;
import com.xworkz.xworkzProject.repo.EmployeeRepo;
import com.xworkz.xworkzProject.repo.HistoryRepo;
import com.xworkz.xworkzProject.repo.SignUpRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private HistoryRepo historyRepo;

    @Autowired
    private SignUpRepo signUpRepo;

    @Override
    public Optional<EmployeeDTO> findByEmail(String email) {
        Optional<EmployeeDTO> optional = employeeRepo.findByEmail(email);
        if (optional.isPresent()) {
            log.info("Email is found");
            return optional;
        } else {
            log.info("Email is not found");
        }
        return Optional.empty();
    }

    @Override
    public Optional<EmployeeDTO> findByPhone(long phone) {

        return employeeRepo.findByPhone(phone);
    }


    @Override
    public List<ComplaintsDTO> getAssignedComplaints(int employeeId) {
        return employeeRepo.findAssignedComplaintsByEmployeeId(employeeId);
    }

    @Override
    public void updateComplaintStatus(int complaintId, String status, String comment) {
        ComplaintsDTO complaint = complaintRepo.findById(complaintId);
        if (complaint != null) {
            complaint.setStatus(status);
            if (comment != null && !comment.isEmpty()) {
                complaint.setComments(comment);
            }
            complaintRepo.update(complaint);
        }
    }

    @Override
    public boolean verifyOtp(int complaintId, String otp) {
        ComplaintsDTO complaint = complaintRepo.findById(complaintId);
        if (complaint != null && complaint.getOtp().equals(otp)) {
            return true;
        }
        return false;
    }

    @Override
    public void saveResolvedComment(int complaintId, String comment) {
        ComplaintsDTO complaint = complaintRepo.findById(complaintId);

        sendOtpToUser(complaintId);
        if (complaint != null) {
            complaint.setComments(comment);

            complaintRepo.update(complaint);

        }
    }

    @Override
    public Optional<EmployeeDTO> validatePhone(Long phone) {
        log.info("Running validatePhone in Service");
        long count = employeeRepo.countByPhone(phone);
        if (count >= 1) {
            return Optional.of(new EmployeeDTO());
        }

        return Optional.empty();
    }

    @Override
    public Optional<EmployeeDTO> validateEmail(String email) {
        log.info("Running validateEmail in Service");
        long count = employeeRepo.countByEmail(email);
        if (count >= 1) {
            return Optional.of(new EmployeeDTO());
        }
        return Optional.empty();
    }

    //send otp to employee
    public String generateAndSendOtp(EmployeeDTO employee) {
        employee.setModifiedAt(LocalDateTime.now());
        employee.setModifiedBy(employee.getName());
        String otp = generateRandomOtp();
        employee.setPassword(otp); // Set OTP directly in EmployeeDTO
        employee.setOtpGeneratedTime(LocalDateTime.now()); // Set OTP generation time
        employeeRepo.save(employee);
        employeeRepo.updateOtp(employee.getEmail(), otp);
        sendOtpEmail(employee, otp);
        return otp;
    }

    // send otp to employee email
    @Override
    public void sendOtpEmail(EmployeeDTO employee, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(employee.getEmail());
        message.setSubject("Your OTP for Login");
        message.setText("Dear " + employee.getName() + ",\n\n" +
                "Your OTP for login is: " + otp + "\n\n" +
                "This OTP will expires in 1 minute\n"+
                "Thanks and Regards,\n" +
                "XworkzProject Team");
        emailSender.send(message);
    }

    private String generateRandomOtp() {
        int length = 6; // desired OTP length
        String characterSet = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterSet.length());
            otp.append(characterSet.charAt(index));
        }

        return otp.toString();
    }

    @Override
    public void saveHistory(HistoryDTO historyDTO) {
        historyRepo.save(historyDTO);
    }

    // sending otp to user
    @Override
    public void sendOtpToUser(int complaintId) {
        // Retrieve the complaint
        ComplaintsDTO complaint = complaintRepo.findById(complaintId);
        if (complaint != null) {
            // Generate OTP
            String otp = generateRandomOtp();
            complaint.setOtp(otp); // Set OTP in complaint
            complaintRepo.save(complaint); // Save the OTP in the complaint entity
            complaintRepo.update(complaint);
            log.info(complaint.getOtp());

            // Send OTP to user's email
            sendOtpToUserEmail(complaint.getUserId(), otp); // Pass complaint's userId
            log.info("OTP sent to user for complaint: " + complaintId);
        }
    }

    // sending otp to user email
    @Override
    public void sendOtpToUserEmail(int userId, String otp) {
        SignUpDTO user = signUpRepo.findByUserId(userId);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Your OTP for Confirmation");
        message.setText("Dear " + user.getFirstName() + ",\n\n" +
                "Your OTP for Confirmation is: " + otp + "\n\n" +
                "Thanks and Regards,\n" +
                "XworkzProject Team");
        emailSender.send(message);
    }

    @Override
    public boolean verifyUserOtp(int complaintId, String otp) {
        // Retrieve the complaint
        ComplaintsDTO complaint = complaintRepo.findById(complaintId);
        if (complaint != null && complaint.getOtp().equals(otp)) {
            return true; // OTP matches
        }
        return false; // OTP does not match
    }


    @Scheduled(fixedRate = 60000) // Run every 1 minute
    public void deleteExpiredOtps() {
        LocalDateTime now = LocalDateTime.now();
        List<EmployeeDTO> employees = employeeRepo.findAll();
        for (EmployeeDTO employee : employees) {
            if (employee.getOtpGeneratedTime() != null && employee.getOtpGeneratedTime().isBefore(now.minusMinutes(1))) {
                employee.setPassword(null); // Remove OTP
                employee.setOtpGeneratedTime(null); // Reset OTP generation time
                employeeRepo.save(employee); // Update the employee record
            }
        }

    }

    @Override
    public boolean markAsRead(int id) {
        employeeRepo.markNotificationAsRead(id);
        return true;
    }

    @Override
    public List<ComplaintsDTO> getUnreadNotifications() {
        return employeeRepo.findUnread();
    }

}