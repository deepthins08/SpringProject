package com.xworkz.xworkzProject.controller;

import com.xworkz.xworkzProject.dto.*;
import com.xworkz.xworkzProject.service.DepartmentAdminService;
import com.xworkz.xworkzProject.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Controller
@RequestMapping("/departmentAdmin")
@Slf4j
public class DepartmentAdminController {

    @Autowired
    private DepartmentAdminService departmentAdminService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DepartmentAdminController(){
        log.info("Created DepartmentAdminController");
    }

    @GetMapping("/signin")
    public String getAdminSignIn(AdminDTO adminDTO, Model model) {
        model.addAttribute("departmentAdmin", true);
        return "SignIn";
    }

    @PostMapping("/signin")
    public String postAdminSignIn(@RequestParam String email,
                                  @RequestParam String password,
                                  HttpSession session,
                                  Model model) {
        Optional<DepartmentAdminDTO> optionalAdmin = departmentAdminService.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            DepartmentAdminDTO admin = optionalAdmin.get();
            if (admin.getPassword().equals(password)) {
                session.setAttribute("departmentAdmin", admin);
                log.info("Passwords are matched");
                model.addAttribute("departmentAdmin", admin);
                return "DepartmentAdmin";
            } else {
                log.info("Invalid Email or password. Please try again.");
                model.addAttribute("errorsMsg", "Invalid Email or password. Please try again.");
            }
        } else {
            log.info("Email or Password no found.");
            model.addAttribute("errorsMsg", "Email or Password no found.");
        }
        return "SignIn";
    }

    @GetMapping("/addEmployee")
    public String showAddEmployeeForm(Model model,EmployeeDTO employeeDTO) {
        List<DepartmentDTO> departments = departmentAdminService.getAllDepartments();

        model.addAttribute("employeeDTO",employeeDTO);
        model.addAttribute("departments", departments);// Initialize form backing object
        return "AddEmployee"; // Return the form view
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute("employeeDTO") EmployeeDTO employeeDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.getDefaultMessage()));
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("dto", employeeDTO);
            return "AddEmployee";
        }


        Optional<EmployeeDTO> existingUser = employeeService.validateEmail(employeeDTO.getEmail());
        if (existingUser.isPresent()) {
            model.addAttribute("addMsg", "Email is already registered. Cannot register user.");
            model.addAttribute("dto",employeeDTO);
            return "AddEmployee";
        }

        Optional<EmployeeDTO> phone = employeeService.validatePhone(employeeDTO.getPhone());
        if (phone.isPresent()) {
            model.addAttribute("addMsg", "Phone is already registered. Cannot register user.");
            model.addAttribute("dto",employeeDTO);
            return "AddEmployee";
        }

        boolean saved = departmentAdminService.validateAndSave(employeeDTO);
        if (saved) {
            model.addAttribute("departmentMsg", "Employee added successfully");
        } else {
            model.addAttribute("errors", "Error adding employee. Please try again.");
        }

        model.addAttribute("employeeDTO", new EmployeeDTO());
        model.addAttribute("departments", departmentAdminService.getAllDepartments());
        return "AddEmployee";
    }



    @GetMapping("/departmentAdminViewComplaints")
    public String updateDepartmentAndStatus(@RequestParam(required = false) String departmentName,
            @RequestParam(required = false) String clear, Model model) {
        departmentAdminService.getAllComplaints();
        List<ComplaintsDTO> complaints;

        if (departmentName == null || departmentName.isEmpty() || "Clear".equals(clear)) {
            complaints = departmentAdminService.getAllComplaints();

        } else {
            complaints = departmentAdminService.getComplaintsByDepartmentName(departmentName);
        }

        // Fetch all employees by department ID
        Map<Integer, List<EmployeeDTO>> employeesByDepartment = new HashMap<>();
        for (ComplaintsDTO complaint : complaints) {
            Integer departmentId = complaint.getDepartmentId();
            List<EmployeeDTO> departmentEmployees = departmentAdminService.getEmployeesByDepartmentId(departmentId);
            employeesByDepartment.put(complaint.getId(), departmentEmployees);
        }

        // Add complaints and employees by department to the model
        model.addAttribute("complaint", complaints);
        model.addAttribute("employeesByDepartment", employeesByDepartment);
        model.addAttribute("selectedType", departmentName); // Optional for selecting dropdown

        return "DepartmentAdminViewComplaints";
    }


    @PostMapping("/updateDepartmentAndStatus")
    public String updateDepartmentAndStatus(@RequestParam int id,
                                            @RequestParam(required = false) Integer employeeId,
                                            @RequestParam(required = false) String status,
                                            Model model) {
        if (status != null && !status.isEmpty() && !status.equals("0")) {
            departmentAdminService.updateComplaintStatus(id, status);
        }
        if (employeeId != null && employeeId != 0) {
            departmentAdminService.updateComplaintEmployee(id, employeeId);
        }

        List<ComplaintsDTO> complaints = departmentAdminService.getAllComplaints(); // Assuming you fetch all complaints again
        model.addAttribute("complaint", complaints);




        return "redirect:/departmentAdmin/departmentAdminViewComplaints";
    }

    @GetMapping("/departmentAdminLogout")
    public String logout(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "redirect:/departmentAdmin/signin";
    }

}
