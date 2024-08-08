package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.repo.EditInfoRepo;
import com.xworkz.xworkzProject.repo.SignUpRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EditInfoServiceImpl implements EditInfoService{

    @Autowired
    private EditInfoRepo editInfoRepo;

    @Autowired
    private SignUpRepo signUpRepo;

    public SignUpDTO updateUserProfile(String email, SignUpDTO signUpDTO) {
        SignUpDTO existingUser = editInfoRepo.findByEmail(email);
        if (existingUser != null) {
            // Update the existing user's profile with new information
            existingUser.setFirstName(signUpDTO.getFirstName());
            existingUser.setLastName(signUpDTO.getLastName());
            existingUser.setPhone(signUpDTO.getPhone());
            existingUser.setProfileImage(signUpDTO.getProfileImage());


            // Save the updated user profile
            return editInfoRepo.updateProfile(existingUser);
        } else {
            // User not found with the given email
            return null;
        }
    }




}
