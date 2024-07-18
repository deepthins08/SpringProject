package com.xworkz.xworkzProject.resources;

import com.xworkz.xworkzProject.dto.EmployeeDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.DepartmentAdminService;
import com.xworkz.xworkzProject.service.EmployeeService;
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

    @Autowired
    private EmployeeService employeeService;

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

    @GetMapping("departmentAdmin/emailValidation")
    public String validateMail(@RequestParam String email) {
        Optional<EmployeeDTO> optional = employeeService.findByEmail(email);
        return optional.isPresent() ? "Email is already Exist" : "";
    }

    @GetMapping("departmentAdmin/phoneValidation")
    public String validateMobile(@RequestParam Long phone) {
        Optional<EmployeeDTO> existingPhone = employeeService.findByPhone(phone);
        return existingPhone.isPresent() ? "Phone is already Exist": ""; // Return true if phone number already exists
    }
}
