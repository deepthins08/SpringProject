package com.xworkz.xworkzProject.controller;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.ComplaintService;
import com.xworkz.xworkzProject.service.SignUpService;
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
import java.util.Optional;

@Controller
@RequestMapping("/")
public class RaiseComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private SignUpService signUpService;

    RaiseComplaintController(){
        System.out.println("Created RaiseComplaintController");
    }

    @PostMapping("/raiseComplaint")
    public String setComplaint(ComplaintsDTO complaintsDTO, Model model, HttpSession session, @RequestParam String btn) {
        System.out.println("Running setComplaint in RaiseComplaintController");
        System.out.println("running setComplaint in controller, submit value : " + btn);
        boolean edit = btn .equalsIgnoreCase("Edit");
        // Retrieve the userId from the session
        int userId = (int) session.getAttribute("userId");
        if (userId == 0) {
            model.addAttribute("complaintMsg", "User not logged in.");
            return "RaiseComplaint";
        }


        SignUpDTO signUpDTO = signUpService.findById(userId);

        complaintsDTO.setCreatedBy(signUpDTO.getFirstName() + " " + signUpDTO.getLastName());
        complaintsDTO.setCreatedAt(LocalDateTime.now());

        // Set the userId in the ComplaintsDTO
        complaintsDTO.setUserId(userId);
        if (!edit) {
            boolean save = complaintService.validateAndSave(complaintsDTO);
            if (save) {
                model.addAttribute("complaintMsg", "Your Complaint is Received");
                model.addAttribute("complaint", complaintsDTO);

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
            } else {
                model.addAttribute("complaintMsg", "Failed to update the complaint, Please try again");
            }
        }
        return "RaiseComplaint";
    }


    @GetMapping("/viewComplaints")
    public String viewComplaints(Model model, HttpSession session,
                                 @RequestParam(value = "status", required = false) String status) {
        // Retrieve the userId from the session
        int userId = (int) session.getAttribute("userId");
        if (userId == 0) {
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
        int userId = (int) session.getAttribute("userId");
        if (userId == 0) {
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

    @GetMapping("/raiseComplaintPage")
    public String raiseComplaintPage(Model model){
        model.addAttribute("action","submit");
        return "RaiseComplaint";
    }

    @PostMapping("/editComplaintData")
    public String editComplaintData(ComplaintsDTO complaintsDTO, Model model, HttpSession session) {

        boolean update = complaintService.validateAndUpdate(complaintsDTO);

        if (update) {
            model.addAttribute("complaintMsg", "Complaint successfully updated.");
            model.addAttribute("complaint", complaintsDTO);
        } else {
            model.addAttribute("complaintMsg", "Failed to update the complaint, Please try again");
        }

//        model.addAttribute("complaint", complaint);
        model.addAttribute("action", "edit");
        return "RaiseComplaint";
    }

}
