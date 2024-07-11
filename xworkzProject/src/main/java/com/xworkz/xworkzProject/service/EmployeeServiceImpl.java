package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.EmployeeDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.repo.EmployeeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public Optional<EmployeeDTO> findByEmail(String email) {
      Optional<EmployeeDTO> optional=  employeeRepo.findByEmail(email);
      if(optional.isPresent()){
          log.info("Email is found");
          return optional;
      }else {
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
    public void updateComplaintStatus(int complaintId, String status) {
        employeeRepo.updateComplaintStatus(complaintId, status);
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

    public String generateAndSendOtp(EmployeeDTO employee) {
        String otp = generateRandomOtp();
        employee.setPassword(otp); // Set OTP directly in EmployeeDTO
        employeeRepo.save(employee);
        employeeRepo.updateOtp(employee.getEmail(), otp);
        sendOtpEmail(employee, otp);
        return otp;
    }


    public void sendOtpEmail(EmployeeDTO employee, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(employee.getEmail());
        message.setSubject("Your OTP for Login");
        message.setText("Dear " + employee.getName() + ",\n\n" +
                "Your OTP for login is: " + otp + "\n\n" +
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
}
