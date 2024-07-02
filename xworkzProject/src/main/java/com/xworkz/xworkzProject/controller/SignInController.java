package com.xworkz.xworkzProject.controller;

import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class SignInController {

    @Autowired
    private SignUpService signUpService;

    SignInController() {
        System.out.println("Created SignInController");
    }

    @PostMapping("/signIn")
    public String getDetails(SignUpDTO signUpDTO, Model model, HttpServletRequest request) {
        Optional<SignUpDTO> userOptional = signUpService.findByEmail(signUpDTO.getEmail());
        if (userOptional.isPresent()) {
            SignUpDTO user = userOptional.get();
            System.out.println(user);

            if(user.getCount() == 0 && user.getPassword().equals(signUpDTO.getPassword())){
                signUpService.updateLoginCount(user.getId());
                model.addAttribute("dto", user);
                model.addAttribute("message", "Login successful");

                // Store userId in session
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());

                return "PasswordResetPage";
            }

            if (signUpDTO.getPassword().equals(user.getNewPassword())) {
                if (user.getCount() == 0) {
                    signUpService.updateLoginCount(user.getId());
                    model.addAttribute("dto", user);
                    model.addAttribute("message", "Login successful");

                    // Store userId in session
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", user.getId());

                    return "PasswordResetPage";
                } else {
                    //setting properties for whole session for user
                    HttpSession httpSession= request.getSession();
                    httpSession.setAttribute("email",userOptional.get().getEmail());
                    httpSession.setAttribute("firstName",userOptional.get().getFirstName());
                    httpSession.setAttribute("lastName",userOptional.get().getLastName());
                    httpSession.setAttribute("phone",userOptional.get().getPhone());
                    httpSession.setAttribute("action","edit");
//                    httpSession.setAttribute("image",userOptional.get().getProfileImage());
                    signUpService.updateLoginCount(user.getId());
                    httpSession.setAttribute("userId", user.getId());

//                    set session for displaying image
                    String profileImageUrl="/images/"+user.getProfileImage();
                    httpSession.setAttribute("profileImage",profileImageUrl);

                    model.addAttribute("dto", user);
                    model.addAttribute("message", "Login Successful, Welcome Back");
                    return "Home";
                }
            } else {
                signUpService.incrementFailedAttempts(user.getId());
                int attemptsLeft = 2 - user.getFailedAttempts();
                if (attemptsLeft > 0) {
                    model.addAttribute("errorsMsg", "Incorrect Password or Email. You have " + attemptsLeft + " attempts left.");
                } else {
                    signUpService.lockAccount(user.getId());
                    model.addAttribute("errorsMsg", "Account locked due to multiple failed login attempts. Please reset your password.");
                    model.addAttribute("userLocked", true);
                }
            }
        } else {
            model.addAttribute("errorsMsg", "User not found for email");
        }
        return "SignIn";


    }

    @GetMapping("/emailPassword")
    public String sendPasswordToEmail(@RequestParam String email, Model model, SignUpDTO signUpDTO) {
        Optional<SignUpDTO> optional = signUpService.findByEmail(email);
        if (optional.isPresent()) {
            SignUpDTO user = optional.get();

            String newPassword = generateRandomPassword();

            user.setPassword(newPassword);
            user.setFailedAttempts(0);
            signUpService.savePassword(user);


            signUpService.sendPassword(user);

            model.addAttribute("message", "A new password has been sent to your email.");
            model.addAttribute("dto",signUpDTO);
            return "PasswordResetPage";
        } else {
            model.addAttribute("errors", "Email is not registered.");
            return "SignIn";
        }
    }

    // Method to generate a random password (reuse the existing method from the service)
    private String generateRandomPassword() {
        return signUpService.generateRandomPassword();
    }


}



