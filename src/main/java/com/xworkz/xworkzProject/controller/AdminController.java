package com.xworkz.xworkzProject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xworkz.xworkzProject.dto.*;
import com.xworkz.xworkzProject.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

//    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    @Autowired
    private AdminService adminService;

    @Autowired
    private DepartmentAdminService departmentAdminService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/signin")
    public String getAdminSignIn(AdminDTO adminDTO, Model model) {
        model.addAttribute("admin", true);
        return "SignIn";
    }

    @PostMapping("/signin")
    public String postAdminSignIn(@RequestParam String email,
                                  @RequestParam String password,
                                  HttpSession session,
                                  Model model) {
        Optional<AdminDTO> optionalAdmin = adminService.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            AdminDTO admin = optionalAdmin.get();
            if (admin.getPassword().equals(password)) {
//                List<ComplaintsDTO> notifications = complaintService.getUnreadNotifications();
//
//                notifications.forEach(notification -> {
//                    complaintService.markAsRead(notification.getId());
//                });

//                int notificationCount = notifications.size();
//                Map<Integer, String> formattedDates = new HashMap<>();
//                notifications.forEach(notification -> {
//                    Date createdAtDate = complaintService.convertToDateViaInstant(notification.getCreatedAt());
//
//                    String formattedDate = complaintService.formatNotificationDate(createdAtDate);
//                    formattedDates.put(notification.getId(), formattedDate);
//                });
//
//                notifications.sort((n1, n2) -> n2.getCreatedAt().compareTo(n1.getCreatedAt()));
//                session.setAttribute("notifications", notifications);
//                session.setAttribute("notificationCount", notificationCount);
////                model.addAttribute("formattedDates", formattedDates);
//                session.setAttribute("formattedDates",formattedDates);
                session.setAttribute("admin", admin);
                model.addAttribute("admin", admin);
                return "Admin";
            } else {
                model.addAttribute("errorsMsg", "Invalid Email or password. Please try again.");
            }
        } else {
            model.addAttribute("errorsMsg", "Email or Password not found.");
        }
        return "SignIn";
    }

    @GetMapping("/markNotificationAsRead")
    @ResponseBody
    public String markNotificationAsRead(@RequestParam int notificationId, HttpSession session) {
        log.info("Marking notification as read: {}", notificationId);

        boolean success = complaintService.markAsRead(notificationId);
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

    @GetMapping("/getNotifications")
    @ResponseBody
    public void getNotifications( HttpServletResponse response,HttpSession session,AdminDTO admin) {
        try {
            session.setAttribute("admin",admin);
            List<ComplaintsDTO> notifications = complaintService.getUnreadNotifications();
            int notificationCount = notifications.size();

//            // Format dates and times
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

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
                notificationMap.put("read", notification.getRead());
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
//    @GetMapping("/notificationCount")
//    @ResponseBody
//    public int getNotificationCount() {
//        return complaintService.getUnreadNotificationCount();
//    }

//    @GetMapping("/getUnreadNotifications")
//    @ResponseBody
//    public List<ComplaintsDTO> getUnreadNotifications() {
//        return complaintService.getUnreadNotifications();
//    }

    @GetMapping("/viewComplaints")
    public String viewComplaint(@RequestParam("id") int complaintId, Model model) {
        ComplaintsDTO complaint = complaintService.findById(complaintId);
        model.addAttribute("complaint", complaint);
        return "NotificationsViewComplaint"; // Ensure this JSP exists and matches your view
    }
//
//    @GetMapping("/notification/read/{id}")
//    @ResponseBody
//    public void markAsRead(@PathVariable int id) {
//        complaintService.markAsRead(id);
//
//    }
//
//    @GetMapping("/notifications")
//    @ResponseBody
//    public List<ComplaintsDTO> getNotifications() {
//        return complaintService.getUnreadNotifications();
//    }
//
//    @GetMapping("/admin/unreadNotificationCount")
//    @ResponseBody
//    public int getUnreadNotificationCount() {
//        return complaintService.getUnreadNotificationCount();
//    }

    @GetMapping("/viewUsers")
    public String viewUsers(Model model) {
        List<SignUpDTO> users = adminService.getAllUsers();
        log.info("{}",users);
        model.addAttribute("users", users);
        return "viewUsers";
    }

    @GetMapping("/adminViewComplaints")
    public String adminViewComplaints(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) Integer complaintId,
            Model model
    ) {
        List<ComplaintsDTO> complaints;

        if (complaintId != null) {
            // Fetch the specific complaint by ID
            ComplaintsDTO complaint = adminService.getComplaintById(complaintId);
            if (complaint != null) {
                model.addAttribute("complaint", complaint);
                return "AdminViewComplaint";
            } else {
                model.addAttribute("msg", "Complaint not found.");
            }
        }

        complaints=adminService.getAllComplaints();

        log.info("Type:{} " , type , ", Area:{} " , area);
        log.info("Type is empty:{} " , (type == null || type.isEmpty()));
        log.info("Area is empty:{} " , (area == null || area.isEmpty()));

        if ((type == null || type.isEmpty()) && (area == null || area.isEmpty())) {
            complaints = adminService.getAllComplaints(); // Fetch all complaints

        } else {
            List<ComplaintsDTO> list = adminService.searchComplaintsByTypeAndArea(type, area);
            if (!list.isEmpty()) {
                model.addAttribute("complaint", list);
                return "AdminViewComplaint";
            } else {
                model.addAttribute("msg", "Data not found");
                List<ComplaintsDTO> list1 = adminService.searchComplaintsByTypeOrArea(type, area);
                if (!list1.isEmpty()) {
                    model.addAttribute("complaint", list1);
                    return "AdminViewComplaint";
                }
            }
        }



        model.addAttribute("complaint", complaints);

        return "AdminViewComplaint";
    }


    @PostMapping("/updateComplaintAssignAndStatus")
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

    @GetMapping("/addDepartmentAdmin")
    public String showAddEmployeeForm(Model model,DepartmentAdminDTO departmentAdminDTO) {
        List<DepartmentDTO> departments = departmentAdminService.getAllDepartments();

        model.addAttribute("employeeDTO",departmentAdminDTO);
        model.addAttribute("departments", departments);// Initialize form backing object
        return "AddDepartmentAdmin"; // Return the form view
    }

    @PostMapping("/addDepartmentAdmin")
    public String addDepartmentAdmin(@ModelAttribute("departmentAdminDTO") DepartmentAdminDTO departmentAdminDTO,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.getDefaultMessage()));
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("dto", departmentAdminDTO);
            return "AddDepartmentAdmin";
        }


        Optional<DepartmentAdminDTO> existingUser = departmentAdminService.validateAdminEmail(departmentAdminDTO.getEmail());
        if (existingUser.isPresent()) {
            model.addAttribute("addMsg", "Email is already registered. Cannot register user.");
            model.addAttribute("dto",departmentAdminDTO);
            return "AddDepartmentAdmin";
        }

        Optional<DepartmentAdminDTO> phone = departmentAdminService.validateAdminPhone(departmentAdminDTO.getPhone());
        if (phone.isPresent()) {
            model.addAttribute("addMsg", "Phone is already registered. Cannot register user.");
            model.addAttribute("dto",departmentAdminDTO);
            return "AddDepartmentAdmin";
        }

        boolean saved = adminService.validateAndSaveDepartmentAdmin(departmentAdminDTO);
        if (saved) {
            model.addAttribute("departmentMsg", "Department Admin added successfully");
        } else {
            model.addAttribute("errors", "Error adding Department Admin. Please try again.");
        }

        model.addAttribute("departmentDTO", departmentAdminDTO);
        model.addAttribute("departments", departmentAdminService.getAllDepartments());
        return "AddDepartmentAdmin";
    }

    @GetMapping("/downloadComplaints")
    public void downloadComplaints(HttpSession session, HttpServletResponse response) throws IOException {

        List<ComplaintsDTO> complaints = complaintService.getAllComplaints();

        // Set the content type and headers for the response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"complaints.xlsx\"");

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



    @GetMapping("/adminLogout")
    public String logout(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "redirect:/admin/signin";
    }




}
