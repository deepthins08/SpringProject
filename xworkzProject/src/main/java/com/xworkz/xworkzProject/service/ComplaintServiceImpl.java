package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.repo.ComplaintRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService{

    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public boolean validateAndSave(ComplaintsDTO complaintsDTO) {
        System.out.println("Calling validateAndSave method in Service");
       boolean save= complaintRepo.save(complaintsDTO);
       if(save){
           System.out.println("Data is saved successfully");

       }else {
           System.out.println("Data is not saved");
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
        System.out.println("Calling validateAndUpdate method in Service");
        boolean update = complaintRepo.update(complaintsDTO);
        if (update) {
            System.out.println("Data is updated successfully");
        } else {
            System.out.println("Data is not updated");
        }
        return update;
    }

}
