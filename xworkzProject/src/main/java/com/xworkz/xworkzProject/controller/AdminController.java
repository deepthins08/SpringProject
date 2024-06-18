package com.xworkz.xworkzProject.controller;

import com.xworkz.xworkzProject.dto.AdminDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.repo.AdminRepo;
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
                model.addAttribute("errorsMsg", "Invalid password. Please try again.");
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
}
