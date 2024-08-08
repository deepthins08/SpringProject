package com.xworkz.xworkzProject.controller;

import com.xworkz.xworkzProject.dto.DepartmentAdminDTO;
import com.xworkz.xworkzProject.dto.ResetPasswordDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.DepartmentAdminService;
import com.xworkz.xworkzProject.service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@Slf4j
public class PasswordSetController {
    @Autowired
    private SignUpService signUpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DepartmentAdminService departmentAdminService;

    @PostMapping("/resetPassword")
    public String resetPassword(@Valid ResetPasswordDTO resetPasswordDTO, BindingResult bindingResult, Model model, HttpSession session) {

        log.info("reset============================");
        log.info("{} ",resetPasswordDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("dto", resetPasswordDTO);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "PasswordResetPage";
        }
        if (!resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getConfirmPassword())) {
           log.info("Passwords are not matched");
            model.addAttribute("dto", resetPasswordDTO);
            model.addAttribute("message", "new Password and Confirm password are not matches");
            return "PasswordResetPage";
        }

        Optional<SignUpDTO> userOptional = signUpService.findByEmail(resetPasswordDTO.getEmail());
        if (userOptional.isPresent()) {

            SignUpDTO user = userOptional.get();
            if (passwordEncoder.matches(resetPasswordDTO.getPassword(),user.getPassword())) {
                log.info("password is resetting.....");
                String encodePassword=passwordEncoder.encode(resetPasswordDTO.getNewPassword());
                user.setNewPassword(encodePassword);
                signUpService.savePassword(user);
                session.setAttribute("action","edit");
                model.addAttribute("message", "Password reset successful. Please login with your new password.");
                return "SignIn";
            }

        }
        Optional<DepartmentAdminDTO> departmentAdmin = departmentAdminService.findByEmail(resetPasswordDTO.getEmail());
        if (departmentAdmin.isPresent()) {

            DepartmentAdminDTO user = departmentAdmin.get();
            if (passwordEncoder.matches(resetPasswordDTO.getPassword(),user.getPassword())) {
                log.info("password is resetting.....");
                String encodePassword=passwordEncoder.encode(resetPasswordDTO.getNewPassword());
                user.setNewPassword(encodePassword);
                departmentAdminService.savePassword(user);

                model.addAttribute("message", "Password reset successful. Please login with your new password.");
                return "DepartmentAdminSignIn";
            }

        }


        model.addAttribute("message","Invalid Email or Password");
        return "PasswordResetPage";
    }



}
