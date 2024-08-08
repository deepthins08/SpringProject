package com.xworkz.xworkzProject.controller;


import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/")
@Slf4j
public class SignUpController {
    @Autowired
    private SignUpService signUpService;
    SignUpController(){
        System.out.println("Created SignUpController");
    }
    @PostMapping("/signup")
    public String setSignUp(@Valid SignUpDTO signUpDTO, BindingResult bindingResult, Model model){
        log.info("Running setSignUp in SignUpController");

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.getDefaultMessage()));
            model.addAttribute("errors",bindingResult.getAllErrors());
            model.addAttribute("dto",signUpDTO);

        }else {
            Optional<SignUpDTO> existingUser = signUpService.validateEmail(signUpDTO.getEmail());
            if (existingUser.isPresent()) {
                model.addAttribute("signupMsg", "Email is already registered. Cannot register user.");
                model.addAttribute("dto",signUpDTO);
                return "Signup";
            }

            Optional<SignUpDTO> phone=signUpService.validatePhone(signUpDTO.getPhone());
            if(phone.isPresent()){
                model.addAttribute("signupMsg","Phone is already registered.cannot register user");
                model.addAttribute("dto",signUpDTO);
                return "Signup";
            }




          boolean save=  signUpService.ValidateAndSave(signUpDTO);
            if (save) {
                model.addAttribute("signupMsg","Successfully Signed Up "+signUpDTO.getFirstName());
                model.addAttribute("password","Your password is "+signUpDTO.getPassword());
            }else{
                model.addAttribute("signupMsg"," Signed Up is Failed "+signUpDTO.getFirstName());

            }


        }
        return "Signup";
    }





}
