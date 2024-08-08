package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.NotificationsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.repo.ComplaintRepo;
import com.xworkz.xworkzProject.repo.NotificationRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ComplaintServiceImpl implements ComplaintService{

    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private NotificationRepo notificationRepo;

    @Override
    public boolean validateAndSave(ComplaintsDTO complaintsDTO) {
        log.info("Calling validateAndSave method in Service");
       boolean save= complaintRepo.save(complaintsDTO);
       if(save){
           NotificationsDTO notification = new NotificationsDTO();
           notification.setMessage("New complaint raised by user ID: " + complaintsDTO.getUserId());
           notification.setCreatedAt(LocalDateTime.now());
           notification.setRead(false); // Set notification as unread
           notificationRepo.save(notification);
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

    @Override
    public List<ComplaintsDTO> getAllComplaints() {
        return complaintRepo.findAll();
    }

    @Override
    public int getUnreadNotificationCount() {
        return notificationRepo.countUnread();
    }

    public boolean markAsRead(int id) {
        complaintRepo.markNotificationAsRead(id);
        return true;
        }

    @Override
    public List<ComplaintsDTO> getUnreadNotifications() {
        return complaintRepo.findUnread();
    }


    // for formatting Local date to date format
    @Override
    public Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

    //formatting LocalDate to Date to display the date and time only
    @Override
    public String formatNotificationDate(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        SimpleDateFormat todayFormat = new SimpleDateFormat("yyyyMMdd");

        Date now = new Date();
        if (todayFormat.format(now).equals(todayFormat.format(date))) {
            return timeFormat.format(date);
        } else {
            return dateFormat.format(date);
        }
    }


}
