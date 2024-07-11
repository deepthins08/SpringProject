package com.xworkz.xworkzProject.controller;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.ComplaintService;
import com.xworkz.xworkzProject.service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
public class RaiseComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private SignUpService signUpService;

    RaiseComplaintController() {
        System.out.println("Created RaiseComplaintController");
    }

    @GetMapping("/raiseComplaintPage")
    public String raiseComplaintPage(Model model) {
        model.addAttribute("action", "submit");
        return "RaiseComplaint";
    }

    @PostMapping("/raiseComplaint")
    public String setComplaint(ComplaintsDTO complaintsDTO, Model model, HttpSession session, @RequestParam String btn) {
        log.info("Running setComplaint in RaiseComplaintController");
        boolean edit = btn.equalsIgnoreCase("Edit");

        // Retrieve the userId from the session
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null || userId == 0) {
            model.addAttribute("complaintMsg", "User not logged in.");
            return "RaiseComplaint";
        }

        SignUpDTO signUpDTO = signUpService.findById(userId);
        complaintsDTO.setCreatedBy(signUpDTO.getFirstName() + " " + signUpDTO.getLastName());
        complaintsDTO.setCreatedAt(LocalDateTime.now());
        complaintsDTO.setUserId(userId);

        if (!edit) {
            boolean save = complaintService.validateAndSave(complaintsDTO);
            if (save) {
                model.addAttribute("complaintMsg", "Your Complaint is Received");
                model.addAttribute("complaint", complaintsDTO);
                model.addAttribute("action", "submit");

                complaintService.sendEmail(signUpDTO, complaintsDTO);
            } else {
                model.addAttribute("complaintMsg", "Your Complaint is not Received, Please try again");
            }
        } else {
            int complaintId = complaintsDTO.getId();
            ComplaintsDTO existingComplaint = complaintService.findById(complaintId);
            if (existingComplaint == null || existingComplaint.getUserId() != userId) {
                model.addAttribute("complaintMsg", "No such complaint found.");
                return "ViewComplaint";
            }

            complaintsDTO.setId(complaintId);
            boolean update = complaintService.validateAndUpdate(complaintsDTO);
            if (update) {
                model.addAttribute("complaintMsg", "Complaint successfully updated.");
                model.addAttribute("complaint", complaintsDTO);
                model.addAttribute("action", "edit");
            } else {
                model.addAttribute("complaintMsg", "Failed to update the complaint, Please try again");
            }
        }
        return "RaiseComplaint";
    }

    @GetMapping("/viewComplaints")
    public String viewComplaints(Model model, HttpSession session, @RequestParam(value = "status", required = false) String status) {
        // Retrieve the userId from the session
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null || userId == 0) {
            model.addAttribute("complaintMsg", "User not logged in.");
            return "ViewComplaint";
        }
        if (status == null) {
            status = "active"; // default to "active" if no status is provided
        }
        List<ComplaintsDTO> complaints = complaintService.getComplaintsByUserIdAndStatus(userId, status);
        if (complaints.isEmpty()) {
            model.addAttribute("complaintMsg", "No complaints found");
        } else {
            model.addAttribute("complaints", complaints);
        }
        model.addAttribute("selectedStatus", status);
        return "ViewComplaint";
    }

    @GetMapping("/editComplaint")
    public String editComplaintPage(@RequestParam("id") int id, Model model, HttpSession session) {
        session.setAttribute("id", id);
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null || userId == 0) {
            model.addAttribute("complaintMsg", "User not logged in.");
            return "RaiseComplaint";
        }
        ComplaintsDTO complaint = complaintService.findById(id);
        if (complaint == null || complaint.getUserId() != userId) {
            model.addAttribute("complaintMsg", "No such complaint found.");
            return "ViewComplaint";
        }
        model.addAttribute("complaint", complaint);
        model.addAttribute("action", "edit");
        return "RaiseComplaint";
    }
}
