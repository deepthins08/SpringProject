package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.repo.EditInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditInfoServiceImpl implements EditInfoService{

    @Autowired
    private EditInfoRepo editInfoRepo;

    public SignUpDTO updateUserProfile(String email, SignUpDTO signUpDTO) {
        SignUpDTO existingUser = editInfoRepo.findByEmail(email);
        if (existingUser != null) {
            // Update the existing user's profile with new information
            existingUser.setFirstName(signUpDTO.getFirstName());
            existingUser.setLastName(signUpDTO.getLastName());
            existingUser.setPhone(signUpDTO.getPhone());
            // Set other properties as needed

            // Save the updated user profile
            return editInfoRepo.updateProfile(existingUser);
        } else {
            // User not found with the given email
            return null;
        }
    }
}
