package com.xworkz.xworkzProject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xworkz.xworkzProject.dto.*;
import com.xworkz.xworkzProject.service.ComplaintService;
import com.xworkz.xworkzProject.service.DepartmentAdminService;
import com.xworkz.xworkzProject.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")
@Slf4j
public class DepartmentAdminController {

    @Autowired
    private DepartmentAdminService departmentAdminService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ComplaintService complaintService;

    public DepartmentAdminController(){
        log.info("Created DepartmentAdminController");
    }

    @GetMapping("/departmentAdminSignIn")
    public String showDepartments(Model model, EmployeeDTO employeeDTO) {
        List<DepartmentDTO> departments = departmentAdminService.getAllDepartments();
        model.addAttribute("employeeDTO", employeeDTO);
        model.addAttribute("departments", departments);
        return "DepartmentAdminSignIn";
    }

    @PostMapping("/departmentAdminSignIn")
    public String postAdminSignIn(@RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam Integer departmentId,
                                  HttpServletRequest request,
                                  Model model) {
        Optional<DepartmentAdminDTO> departmentAdmin = departmentAdminService.findAdminByDepartmentId(departmentId);
        if (departmentAdmin.isPresent()) {
            DepartmentAdminDTO admin = departmentAdmin.get();
            if (validatePasswordAndLoginCount(admin, password, model)) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", admin.getId());
                session.setAttribute("departmentId", departmentId);
                log.info("Department ID set in session: {}", departmentId);

                if (admin.getLoginCount() == 0) {
                    return "PasswordResetPage";
                } else {
                    session.setAttribute("departmentAdmin", admin);
                    List<ComplaintsDTO> notifications = departmentAdminService.getNotificationsForAdmin(departmentId);
                    int notificationCount = notifications.size();
                    Map<Integer, String> formattedDates = new HashMap<>();
                    notifications.forEach(notification -> {
                        Date modifiedDate = complaintService.convertToDateViaInstant(notification.getModifiedAt());
                        String formattedDate = complaintService.formatNotificationDate(modifiedDate);
                        formattedDates.put(notification.getId(), formattedDate);
                    });
                    notifications.sort((n1,n2)->n2.getModifiedAt().compareTo(n1.getModifiedAt()));

                    // Fetch notifications for the department admin
                    session.setAttribute("notifications",notifications);
//                    model.addAttribute("notifications", notifications);
                    model.addAttribute("formattedDates", formattedDates);
                    // Set the notification count in the session or model
                    session.setAttribute("notificationCount",notificationCount);
//                    model.addAttribute("notificationCount", notificationCount);
                    return "DepartmentAdmin";
                }
            } else {
                model.addAttribute("errorsMsg", "Invalid Email or password. Please try again.");
            }
        } else {
            model.addAttribute("errorsMsg", "Email or Password not found.");
        }
        return "DepartmentAdminSignIn";
    }

    private boolean validatePasswordAndLoginCount(DepartmentAdminDTO admin, String password, Model model) {
        if (passwordEncoder.matches(password, admin.getPassword()) || passwordEncoder.matches(password, admin.getNewPassword())) {
            departmentAdminService.updateLoginCount(admin.getId());
            model.addAttribute("dto", admin);
            model.addAttribute("message", "Login successful");
            return true;
        }
        return false;
    }

    @GetMapping("/addEmployee")
    public String showAddEmployeeForm(Model model, EmployeeDTO employeeDTO) {
        List<DepartmentDTO> departments = departmentAdminService.getAllDepartments();
        model.addAttribute("employeeDTO", employeeDTO);
        model.addAttribute("departments", departments);
        return "AddEmployee";
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
            model.addAttribute("dto", employeeDTO);
            return "AddEmployee";
        }

        Optional<EmployeeDTO> phone = employeeService.validatePhone(employeeDTO.getPhone());
        if (phone.isPresent()) {
            model.addAttribute("addMsg", "Phone is already registered. Cannot register user.");
            model.addAttribute("dto", employeeDTO);
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
    public String departmentViewComplaints(@RequestParam(required = false) String departmentName,
                                           @RequestParam(required = false) String clear,
                                           HttpServletRequest request,
                                           ComplaintsDTO complaintsDTO,
                                           Model model) {

        HttpSession session = request.getSession();
        Integer departmentId = (Integer) session.getAttribute("departmentId");
        log.info("Department ID from session: {}", departmentId);

        if (departmentId == null) {
            model.addAttribute("errorsMsg", "Department ID is not set in the session.");
            return "DepartmentAdminSignIn";
        }

        List<ComplaintsDTO> complaints;
        if (departmentName == null || departmentName.isEmpty() || "Clear".equals(clear)) {
            complaints = departmentAdminService.getComplaintsByDepartmentId(departmentId);
        } else {
            complaints = departmentAdminService.getComplaintsByDepartmentName(departmentName);
        }

        Map<Integer, List<EmployeeDTO>> employeesByDepartment = new HashMap<>();
        for (ComplaintsDTO complaint : complaints) {
            Integer deptId = complaint.getDepartmentId();
            List<EmployeeDTO> departmentEmployees = departmentAdminService.getEmployeesByDepartmentId(deptId);
            employeesByDepartment.put(complaint.getId(), departmentEmployees);
        }

        model.addAttribute("complaint", complaints);
        model.addAttribute("employeesByDepartment", employeesByDepartment);
        model.addAttribute("selectedType", departmentName);

        return "DepartmentAdminViewComplaints";
    }

    @PostMapping("/updateDepartmentAndStatus")
    public String updateDepartmentAndStatus(@RequestParam int id,
                                            @RequestParam(required = false) Integer employeeId,
                                            @RequestParam(required = false) String status,
                                            ComplaintsDTO complaintsDTO,
                                            Integer departmentId,
                                            Model model) {
        if (status != null && !status.isEmpty() && !status.equals("0")) {
            departmentAdminService.updateComplaintStatus(id, status);
        }
        if (employeeId != null && employeeId != 0) {
            departmentAdminService.updateComplaintEmployee(id, employeeId);
        }

        List<ComplaintsDTO> complaints = departmentAdminService.getComplaintsByDepartmentId(departmentId);
        model.addAttribute("complaint", complaints);

        return "redirect:/departmentAdminViewComplaints";
    }


    @GetMapping("/downloadDepartmentComplaints")
    public void downloadComplaints(HttpSession session, HttpServletResponse response) throws IOException {

        Integer departmentId = (Integer) session.getAttribute("departmentId");

        List<ComplaintsDTO> complaints = departmentAdminService.getComplaintsByDepartmentId(departmentId);

        // Set the content type and headers for the response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"DepartmentComplaints.xlsx\"");

        // Create Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Complaints");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Type");
        headerRow.createCell(2).setCellValue("Description");
        headerRow.createCell(3).setCellValue("Status");

        // Populate data rows
        int rowNum = 1; // Start from row 1 (after header)
        for (ComplaintsDTO complaint : complaints) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(complaint.getId());
            row.createCell(1).setCellValue(complaint.getType());
            row.createCell(2).setCellValue(complaint.getDescription());
            row.createCell(3).setCellValue(complaint.getStatus());
        }

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Write workbook to response output stream
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @GetMapping("/markDepartmentNotificationAsRead")
    @ResponseBody
    public String markDepartmentNotificationAsRead(@RequestParam int notificationId, HttpSession session) {
        log.info("Marking notification as read: {}", notificationId);

        boolean success = departmentAdminService.markAsRead(notificationId);
        if (success) {
            // Update session attributes
            List<ComplaintsDTO> notifications = (List<ComplaintsDTO>) session.getAttribute("notifications");
            notifications.removeIf(notification -> notification.getId() == notificationId);
            session.setAttribute("notifications", notifications);
            session.setAttribute("notificationCount", notifications.size());
            return "success";
        }
        return "error";
    }

    @GetMapping("/getDepartmentNotifications")
    @ResponseBody
    public void getDepartmentNotifications( HttpServletResponse response,HttpSession session,AdminDTO admin) {
        try {
            session.setAttribute("admin",admin);
            List<ComplaintsDTO> notifications = departmentAdminService.getUnreadNotifications();
            int notificationCount = notifications.size();

            // Format dates and times
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            List<Map<String, Object>> formattedNotifications = new ArrayList<>();
            Map<Integer, String> formattedDates = new HashMap<>();
            notifications.forEach(notification -> {
                Date createdAtDate = complaintService.convertToDateViaInstant(notification.getCreatedAt());

                String formattedDate = complaintService.formatNotificationDate(createdAtDate);
                formattedDates.put(notification.getId(), formattedDate);
            });
            for (ComplaintsDTO notification : notifications) {
                Map<String, Object> notificationMap = new HashMap<>();
                notificationMap.put("id", notification.getId());
                notificationMap.put("type", notification.getType());
                notificationMap.put("area", notification.getArea());
                notificationMap.put("address", notification.getAddress());
//                notificationMap.put("formattedDate", dateFormat.format(notification.getModifiedAt()));

                notificationMap.put("formattedTime", formattedDates);
                notificationMap.put("read", notification.getDepartmentRead());
                formattedNotifications.add(notificationMap);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("notificationCount", notificationCount);
            result.put("notifications", formattedNotifications);

            notifications.sort((n1, n2) -> n2.getCreatedAt().compareTo(n1.getCreatedAt()));
            session.setAttribute("notifications", notifications);
            session.setAttribute("notificationCount", notificationCount);
//                model.addAttribute("formattedDates", formattedDates);
            session.setAttribute("formattedDates",formattedDates);

            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        } catch (Exception e) {
            log.error("Error fetching notifications", e);
            try {
                response.getWriter().write("{}");
            } catch (IOException ioException) {
                log.error("Error writing response", ioException);
            }
        }
    }

    @GetMapping("/viewDepartmentComplaints")
    public String viewComplaint(@RequestParam("id") int complaintId, Model model) {
        ComplaintsDTO complaint = complaintService.findById(complaintId);
        model.addAttribute("complaint", complaint);
        return "DepartmentNotificationViewComplaints"; // Ensure this JSP exists and matches your view
    }

    @GetMapping("/departmentAdminLogout")
    public String logout(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "redirect:/departmentAdminSignIn";
    }
}
