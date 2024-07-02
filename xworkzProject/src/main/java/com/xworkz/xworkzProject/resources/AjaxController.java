package com.xworkz.xworkzProject.resources;

import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("/")
public class AjaxController {


    @Autowired
    private SignUpService signUpService;

    @GetMapping("/validateEmail")
    public String validateEmail(@RequestParam String email) {
        Optional<SignUpDTO> optional = signUpService.findByEmail(email);
        return optional.isPresent() ? "Email is already Exist" : "";
    }

    @GetMapping("/validatePhone")
    public String validatePhone(@RequestParam Long phone) {
        Optional<SignUpDTO> existingPhone = signUpService.findByPhone(phone);
        return existingPhone.isPresent() ? "Phone is already Exist": ""; // Return true if phone number already exists
    }
}
