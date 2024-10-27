package com.xworkz.xworkzProject.controller;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.ComplaintService;
import com.xworkz.xworkzProject.service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
public class RaiseComplaintController{

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private SignUpService signUpService;

    RaiseComplaintController() {
        System.out.println("Created RaiseComplaintController");
    }

    @GetMapping("/raiseComplaintPage")
    public String raiseComplaintPage(Model model) {
        model.addAttribute("action", "submit");
        return "RaiseComplaint";
    }

    @PostMapping("/raiseComplaint")
    public String setComplaint(ComplaintsDTO complaintsDTO, Model model, HttpSession session, @RequestParam String btn) {
        log.info("Running setComplaint in RaiseComplaintController");
        boolean edit = btn.equalsIgnoreCase("Edit");

        // Retrieve the userId from the session
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null || userId == 0) {
            model.addAttribute("complaintMsg", "User not logged in.");
            return "RaiseComplaint";
        }

        SignUpDTO signUpDTO = signUpService.findById(userId);
        complaintsDTO.setCreatedBy(signUpDTO.getFirstName() + " " + signUpDTO.getLastName());
        complaintsDTO.setCreatedAt(LocalDateTime.now());
        complaintsDTO.setUserId(userId);

        if (!edit) {
            boolean save = complaintService.validateAndSave(complaintsDTO);
            if (save) {
                model.addAttribute("complaintMsg", "Your Complaint is Received");
                model.addAttribute("complaint", complaintsDTO);
                model.addAttribute("action", "submit");

                complaintService.sendEmail(signUpDTO, complaintsDTO);
            } else {
                model.addAttribute("complaintMsg", "Your Complaint is not Received, Please try again");
            }
        } else {
            int complaintId = complaintsDTO.getId();
            ComplaintsDTO existingComplaint = complaintService.findById(complaintId);
            if (existingComplaint == null || existingComplaint.getUserId() != userId) {
                model.addAttribute("complaintMsg", "No such complaint found.");
                return "ViewComplaint";
            }

            complaintsDTO.setId(complaintId);
            boolean update = complaintService.validateAndUpdate(complaintsDTO);
            if (update) {
                model.addAttribute("complaintMsg", "Complaint successfully updated.");
                model.addAttribute("complaint", complaintsDTO);
                model.addAttribute("action", "edit");
            } else {
                model.addAttribute("complaintMsg", "Failed to update the complaint, Please try again");
            }
        }
        return "RaiseComplaint";
    }

    @GetMapping("/viewComplaints")
    public String viewComplaints(Model model, HttpSession session,
                                 @RequestParam(value = "status", required = false) String status,
                                 @RequestParam(defaultValue = "0") int page) {

        // Retrieve the userId from the session
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null || userId == 0) {
            model.addAttribute("complaintMsg", "User not logged in.");
            return "ViewComplaint";
        }
        if (status == null) {
            status = "active"; // default to "active" if no status is provided
        }

        int pageSize = 6;
        Page<ComplaintsDTO> complaints = complaintService.getComplaintsByUserIdAndStatus(userId, status,page,pageSize);
       // Number of records per page
        if (complaints.getTotalPages() == 0) {
            model.addAttribute("complaintMsg", "No complaints found");
            model.addAttribute("hasNext", false);
            model.addAttribute("hasPrevious", false);
        } else {
            model.addAttribute("complaints", complaints.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", complaints.getTotalPages());
            model.addAttribute("hasNext", complaints.hasNext());
            model.addAttribute("hasPrevious", complaints.hasPrevious());
            model.addAttribute("selectedStatus", status);
        }


        model.addAttribute("selectedStatus", status);
        return "ViewComplaint";
    }

    @GetMapping("/editComplaint")
    public String editComplaintPage(@RequestParam("id") int id, Model model, HttpSession session) {
        session.setAttribute("id", id);
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null || userId == 0) {
            model.addAttribute("complaintMsg", "User not logged in.");
            return "RaiseComplaint";
        }
        ComplaintsDTO complaint = complaintService.findById(id);
        if (complaint == null || complaint.getUserId() != userId) {
            model.addAttribute("complaintMsg", "No such complaint found.");
            return "ViewComplaint";
        }
        model.addAttribute("complaint", complaint);
        model.addAttribute("action", "edit");
        return "RaiseComplaint";
    }

    @GetMapping("/downloadComplaints")
    public void downloadComplaints(HttpSession session, HttpServletResponse response) throws IOException {
        // Retrieve the userId from the session
        Integer userId = (Integer) session.getAttribute("userId");
        log.info("User ID from session: {}", userId);
        if (userId == null || userId == 0) {
            // Handle case where user is not logged in
            response.sendRedirect("logout"); // Redirect to logout or login page
            return;
        }

        List<ComplaintsDTO> complaints = complaintService.getComplaintsByUserId(userId);

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





}
