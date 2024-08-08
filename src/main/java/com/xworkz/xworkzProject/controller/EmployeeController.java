package com.xworkz.xworkzProject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xworkz.xworkzProject.dto.AdminDTO;
import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.EmployeeDTO;
import com.xworkz.xworkzProject.service.ComplaintService;
import com.xworkz.xworkzProject.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

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
            model.addAttribute("otpGenerationTime", employee.getOtpGeneratedTime()); // Add this line

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
            session.setAttribute("employee", employee);

            // Check if OTP field is null or expired
            if (employee.getPassword() == null ||
                    (employee.getOtpGeneratedTime() != null && employee.getOtpGeneratedTime().isBefore(LocalDateTime.now().minusMinutes(1)))) {
                model.addAttribute("otpError", "OTP expired. Please request a new one.");
                return "EmployeeSignIn";
            }

            // Validate OTP
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
    public String updateStatus(@RequestParam("id") int complaintId,
                               @RequestParam("status") String status,
                               @RequestParam("comments") String comments,
                               @RequestParam(value = "otp", required = false) String otp,
                               Model model) {
        // Fetch the complaint using the ID
        ComplaintsDTO complaint = complaintService.findById(complaintId);
        if (complaint != null) {
            // If status is resolved, check OTP
            if ("resolved".equals(status)) {
                if (otp == null || !otp.equals(complaint.getOtp())) {
                    model.addAttribute("otpError", "Invalid OTP. Please try again.");
                    log.warn("otp is not valid,please enter valid otp");
                    return "redirect:/employeeViewComplaints"; // Return to the complaints view page
                }
            }
            // Update complaint status and comments
            complaint.setStatus(status);
            complaint.setComments(comments);
            complaintService.validateAndUpdate(complaint);
            model.addAttribute("message", "Complaint status updated successfully.");
        } else {
            model.addAttribute("error", "Complaint not found.");
        }

        // Redirect to the complaints view page or another appropriate page
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
    public String sendOtp(@RequestParam("id") int complaintId, RedirectAttributes redirectAttributes) {
        try {
            employeeService.sendOtpToUser(complaintId);
            redirectAttributes.addFlashAttribute("message", "OTP sent successfully to the user.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to send OTP: " + e.getMessage());
        }
        return "redirect:/employeeViewComplaints";
    }

    @GetMapping("/downloadEmployeeComplaints")
    public void downloadComplaints(HttpSession session, HttpServletResponse response) throws IOException {

        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("employee");


        List<ComplaintsDTO> complaints = employeeService.getAssignedComplaints(employee.getId());

        // Set the content type and headers for the response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"EmployeeComplaints.xlsx\"");

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

    @GetMapping("/markEmployeeNotificationAsRead")
    @ResponseBody
    public String markDepartmentNotificationAsRead(@RequestParam int notificationId, HttpSession session) {
        log.info("Marking notification as read: {}", notificationId);

        boolean success = employeeService.markAsRead(notificationId);
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

    @GetMapping("/getEmployeeNotifications")
    @ResponseBody
    public void getDepartmentNotifications(HttpServletResponse response, HttpSession session, AdminDTO admin) {
        try {
            session.setAttribute("admin",admin);
            List<ComplaintsDTO> notifications = employeeService.getUnreadNotifications();
            int notificationCount = notifications.size();

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

    @GetMapping("/viewEmployeeComplaints")
    public String viewComplaint(@RequestParam("id") int complaintId, Model model) {
        ComplaintsDTO complaint = complaintService.findById(complaintId);
        model.addAttribute("complaint", complaint);
        return "EmployeeNotificationViewComplaints"; // Ensure this JSP exists and matches your view
    }

    @GetMapping("/employeeLogout")
    public String logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "EmployeeSignIn";
    }

}
