package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.repo.ComplaintRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ComplaintServiceImpl implements ComplaintService{

    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public boolean validateAndSave(ComplaintsDTO complaintsDTO) {
        log.info("Calling validateAndSave method in Service");
       boolean save= complaintRepo.save(complaintsDTO);
       if(save){
           log.info("Data is saved successfully");

       }else {
          log.info("Data is not saved");
       }

        return save;
    }

    @Override
    public List<ComplaintsDTO> getComplaintsByUserIdAndStatus(int userId, String status) {
        return complaintRepo.findByUserIdAndStatus(userId, status);
    }


    @Override
    public ComplaintsDTO findById(int id) {  // New method implementation
        return complaintRepo.findById(id);
    }

    @Override
    public void sendEmail(SignUpDTO signUpDTO, ComplaintsDTO complaintsDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(signUpDTO.getEmail());
        message.setSubject("Complaint Received");
        message.setText("Dear " + signUpDTO.getFirstName() + ",\n\nYour complaint has been successfully received." +
                "\n\nComplaint Details:\nId:"+complaintsDTO.getId() +
                "\nType: " + complaintsDTO.getType() +
                "\nDescription: " + complaintsDTO.getDescription() +
                "\n\nBest regards,\nYour Company");
        emailSender.send(message);
    }


    @Override
    public boolean validateAndUpdate(ComplaintsDTO complaintsDTO) {
        log.info("Calling validateAndUpdate method in Service");
        boolean update = complaintRepo.update(complaintsDTO);
        if (update) {
            log.info("Data is updated successfully");
        } else {
            log.info("Data is not updated");
        }
        return update;
    }

    @Override
    public List<ComplaintsDTO> getComplaintsByUserId(int userId) {
        return complaintRepo.findByUserId(userId);
    }

}
