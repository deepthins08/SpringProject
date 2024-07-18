package com.xworkz.xworkzProject.controller;

import com.xworkz.xworkzProject.dto.AdminDTO;
import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.DepartmentAdminDTO;
import com.xworkz.xworkzProject.dto.EmployeeDTO;
import com.xworkz.xworkzProject.service.ComplaintService;
import com.xworkz.xworkzProject.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ComplaintService complaintService;


    @PostMapping("/getOtp")
    public String getOtp(@RequestParam("email") String email, Model model) {
        Optional<EmployeeDTO> optional = employeeService.findByEmail(email);
        if (optional.isPresent()) {
            EmployeeDTO employee = optional.get();
            employeeService.generateAndSendOtp(employee);
            model.addAttribute("message", "OTP sent to your email.");
        } else {
            model.addAttribute("emailError", "Email not found.");
        }
        return "EmployeeSignIn";
    }


    @PostMapping("/loginWithOtp")
    public String loginWithOtp(@RequestParam("email") String email,
                               @RequestParam("otp") String otp,
                               HttpSession session,
                               Model model) {
        Optional<EmployeeDTO> optionalEmployee = employeeService.findByEmail(email);

        if (optionalEmployee.isPresent()) {
            EmployeeDTO employee = optionalEmployee.get();

            session.setAttribute("employee",employee);
            if (employee.getPassword().equals(otp.trim())) {
                model.addAttribute("message", "Login successful. Welcome back!");
                return "Employee";
            } else {
                model.addAttribute("otpError", "Invalid OTP. Please try again.");
            }
        } else {
            model.addAttribute("otpError", "Employee not found.");
        }
        return "EmployeeSignIn";
    }


    @GetMapping("/employeeViewComplaints")
    public String getEmployeeViewComplaints(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("employee");
        if (employee == null) {
            return "EmployeeSignIn";
        }

        // Fetch complaints assigned to this employee
        List<ComplaintsDTO> assignedComplaints = employeeService.getAssignedComplaints(employee.getId());
        model.addAttribute("complaints", assignedComplaints);
        return "EmployeeViewComplaints";
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam("id") int id,
                               @RequestParam("status") String status,
                               @RequestParam("comments") String comments) {
        ComplaintsDTO complaint = complaintService.findById(id);
        if (complaint != null) {
            complaint.setStatus(status);
            complaint.setComments(comments);
            complaintService.validateAndUpdate(complaint);
        }
        return "redirect:/employeeViewComplaints";
    }

    // Handling OTP verification and saving comments for resolved status
    @PostMapping("/verifyOtpAndSaveComment")
    public String verifyOtpAndSaveComment(@RequestParam int id,
                                          @RequestParam String otp,
                                          @RequestParam String comment,
                                          Model model) {
        boolean otpValid = employeeService.verifyOtp(id, otp);
        if (otpValid) {
            employeeService.saveResolvedComment(id, comment);
            model.addAttribute("message", "Complaint resolved successfully!");
        } else {
            model.addAttribute("otpError", "Invalid OTP. Please try again.");
            model.addAttribute("complaintId", id);
            model.addAttribute("otpSectionVisible", true); // To show OTP section in the view
            return "redirect:/employeeViewComplaints"; // Return the view to enter OTP
        }
        return "redirect:/employeeViewComplaints";
    }

    @PostMapping("/sendOtp")
    public String sendOtp(@RequestParam int id, Model model) {
        employeeService.sendOtpToUser(id);
        model.addAttribute("message", "OTP sent to user's email.");
        return "redirect:/employeeViewComplaints"; // Return the view to enter OTP
    }

    @GetMapping("/employeeLogout")
    public String logout(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "EmployeeSignIn";
    }
}
