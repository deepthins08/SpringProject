package com.xworkz.xworkzProject.resources;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.DepartmentAdminDTO;
import com.xworkz.xworkzProject.dto.EmployeeDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;
@RestController
@RequestMapping("/")
public class AjaxController {


    @Autowired
    private SignUpService signUpService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentAdminService departmentAdminService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ComplaintService complaintService;

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


    @GetMapping("validateAdminEmail")
    public String validateAdminEmail(@RequestParam String email) {
        Optional<DepartmentAdminDTO> optional = departmentAdminService.findByEmail(email);
        return optional.isPresent() ? "Email is already Exist" : "";
    }

    @GetMapping("validateAdminPhone")
    public String validateAdminPhone(@RequestParam Long phone) {
        Optional<DepartmentAdminDTO> existingPhone = departmentAdminService.findByPhone(phone);
        return existingPhone.isPresent() ? "Phone is already Exist": ""; // Return true if phone number already exists
    }


    @GetMapping("/validateOtp")
    public String validateOtp(@RequestParam("otp") String otp, @RequestParam("complaintId") int complaintId) {
        ComplaintsDTO complaint = complaintService.findById(complaintId);
        if (complaint != null && otp.equals(complaint.getOtp())) {
            return "";
        }
        return "Invalid OTP. Please try again.";
    }

}
