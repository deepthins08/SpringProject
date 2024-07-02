//package com.xworkz.xworkzProject.controller;
//
//import com.xworkz.xworkzProject.dto.ComplaintsDTO;
//import com.xworkz.xworkzProject.service.AdminService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.Collections;
//import java.util.List;
//
//@Controller
//@RequestMapping("/")
//public class SearchController {
//
//    @Autowired
//    private AdminService adminService;
//
//    public SearchController(){
//        System.out.println("Created SearchController");
//    }
//
//
//    @GetMapping("/adminViewComplaints")
//    public String adminViewComplaints(
//            @RequestParam(required = false) String status,
//            @RequestParam(required = false) String type,
//            @RequestParam(required = false) String area,
//            @RequestParam(required = false) String filter,
//            @RequestParam(required = false) String search,
//            Model model
//    ) {
//        List<ComplaintsDTO> complaints;
//
//        if (filter != null && filter.equals("Filter")) {
//            complaints = adminService.filterComplaintsByStatus(status);
//            model.addAttribute("selectedStatus", status);
//        } else if (search != null && search.equals("Search")) {
//            complaints = adminService.searchComplaintsByTypeAndArea(type, area);
//            model.addAttribute("selectedType", type);
//            model.addAttribute("selectedArea", area);
//        } else {
//            complaints = Collections.emptyList();  // Default empty list if no filter or search applied
//        }
//
//        model.addAttribute("complaints", complaints);
//        return "AdminViewComplaint";
//    }
//}
