package com.xworkz.xworkzProject.controller;

import com.xworkz.xworkzProject.dto.AdminDTO;
import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.AdminService;
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
public class AdminController {


    @Autowired
    private AdminService adminService;

    @GetMapping("/signin")
    public String getAdminSignIn(AdminDTO adminDTO,Model model) {
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
        System.out.println(users);
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

        System.out.println("Type: " + type + ", Area: " + area);
        System.out.println("Type is empty: " + (type == null || type.isEmpty()));
        System.out.println("Area is empty: " + (area == null || area.isEmpty()));

      List<ComplaintsDTO> complaintsDTOS=  adminService.getAllComplaints();
      model.addAttribute("complaint",complaintsDTOS);
     List<ComplaintsDTO> list=  adminService.searchComplaintsByTypeAndArea(type,area);
     if(!list.isEmpty()){
         System.out.println("Fetching data");
         model.addAttribute("complaint",list);
         return "AdminViewComplaint";
     }else{
         model.addAttribute("msg","data not found");
         List<ComplaintsDTO> list1=adminService.searchComplaintsByTypeOrArea(type,area);
         if(!list1.isEmpty()){
             System.out.println("Fetching data");
             model.addAttribute("complaint",list1);
             return "AdminViewComplaint";
         }
     }

        return "AdminViewComplaint";
    }


    @PostMapping("/updateComplaintStatus")
    public String updateComplaintStatus(
            @RequestParam int id, //ComplaintId
            @RequestParam String status,
            Model model
    ) {
        adminService.updateComplaintStatus(id, status);
        List<ComplaintsDTO> complaints = adminService.getAllComplaints(); // Assuming you fetch all complaints again
        model.addAttribute("complaint", complaints);

        return "redirect:/admin/adminViewComplaints"; // Redirect to the complaints view page
    }
}


