package com.xworkz.xworkzProject.controller;

import com.xworkz.xworkzProject.dto.AdminDTO;
import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin")
@Slf4j
public class AdminController {

//    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    @Autowired
    private AdminService adminService;

    @GetMapping("/signin")
    public String getAdminSignIn(AdminDTO adminDTO, Model model) {
        model.addAttribute("admin", true);
        return "SignIn";
    }

    @PostMapping("/signin")
    public String postAdminSignIn(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<AdminDTO> optionalAdmin = adminService.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            AdminDTO admin = optionalAdmin.get();
            if (admin.getPassword().equals(password)) {

                model.addAttribute("admin", admin);
                return "Admin";
            } else {
                model.addAttribute("errorsMsg", "Invalid Email or password. Please try again.");
            }
        } else {
            model.addAttribute("errorsMsg", "Email or Password no found.");
        }
        return "SignIn";
    }

    @GetMapping("/viewUsers")
    public String viewUsers(Model model) {
        List<SignUpDTO> users = adminService.getAllUsers();
        log.info("{}",users);
        model.addAttribute("users", users);
        return "viewUsers";
    }

    @GetMapping("/adminViewComplaints")
    public String adminViewComplaints(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String area,
            Model model
    ) {
        List<ComplaintsDTO> complaints;

        log.info("Type:{} " , type , ", Area:{} " , area);
        log.info("Type is empty:{} " , (type == null || type.isEmpty()));
        log.info("Area is empty:{} " , (area == null || area.isEmpty()));

        List<ComplaintsDTO> complaintsDTOS = adminService.getAllComplaints();
        model.addAttribute("complaint", complaintsDTOS);
        List<ComplaintsDTO> list = adminService.searchComplaintsByTypeAndArea(type, area);
        if (!list.isEmpty()) {
            log.info("Fetching data");
            model.addAttribute("complaint", list);
            return "redirect/adminViewComplaints";
        } else {
            model.addAttribute("msg", "data not found");
            List<ComplaintsDTO> list1 = adminService.searchComplaintsByTypeOrArea(type, area);
            if (!list1.isEmpty()) {
                log.info("Fetching data");
                model.addAttribute("complaint", list1);
                return "AdminViewComplaint";
            }
        }

        return "AdminViewComplaint";
    }


    @PostMapping("/updateComplaintAssignAnsStatus")
    public String updateComplaintStatus(
            @RequestParam int id,
            @RequestParam(required = false) String status,
            @RequestParam("departmentName") String departmentName,
            Model model
    ) {
        if (status != null && !status.isEmpty() && !status.equals("0")) {
            adminService.updateComplaintStatus(id, status);
        }

        List<ComplaintsDTO> complaints = adminService.getAllComplaints(); // Assuming you fetch all complaints again
        model.addAttribute("complaint", complaints);


        if (departmentName != null && !departmentName.isEmpty()) {
            // updating department id
            Integer departmentId = adminService.getDepartmentIdByType(departmentName);
            if (departmentId != null) {
                // Update complaint with department ID
                adminService.updateComplaintDepartment(id, departmentId);
            } else {
                model.addAttribute("msg", "Department not found for the given name.");
            }
        }

        return "redirect:/admin/adminViewComplaints"; // Redirect to the complaints view page
    }




}
