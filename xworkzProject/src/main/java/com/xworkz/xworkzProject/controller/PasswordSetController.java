package com.xworkz.xworkzProject.controller;

import com.xworkz.xworkzProject.dto.ResetPasswordDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class PasswordSetController {
    @Autowired
    private SignUpService signUpService;

    @PostMapping("/resetPassword")
    public String resetPassword(@Valid ResetPasswordDTO resetPasswordDTO, BindingResult bindingResult, Model model) {

        System.out.println("reset============================");
        System.out.println(resetPasswordDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("dto", resetPasswordDTO);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "PasswordResetPage";
        }
        if (!resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getConfirmPassword())) {
            System.out.println("Passwords are not matched");
            model.addAttribute("dto", resetPasswordDTO);
            model.addAttribute("message", "new Password and Confirm password are not matches");
            return "PasswordResetPage";
        }

        Optional<SignUpDTO> userOptional = signUpService.findByEmail(resetPasswordDTO.getEmail());
        if (userOptional.isPresent()) {

            SignUpDTO user = userOptional.get();
            if (user.getPassword().equals(resetPasswordDTO.getPassword())) {
                System.out.println("password is resetting.....");
                user.setNewPassword(resetPasswordDTO.getNewPassword());
                signUpService.savePassword(user);

                model.addAttribute("message", "Password reset successful. Please login with your new password.");
                return "SignIn";
            }

        }
        model.addAttribute("message","Invalid Email or Password");
        return "PasswordResetPage";
    }
}
