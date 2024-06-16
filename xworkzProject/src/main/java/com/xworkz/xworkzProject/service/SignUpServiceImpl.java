package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.message.PasswordGenerator;
import com.xworkz.xworkzProject.repo.SignUpRepo;
import com.xworkz.xworkzProject.repo.SignUpRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService{

    @Autowired
    private SignUpRepo signUpRepo;
    SignUpServiceImpl(){
        System.out.println("Created SignUpServiceImpl");
    }
    @Override
    public boolean ValidateAndSave(SignUpDTO signUpDTO) {
        System.out.println("running save in SignUpServiceImpl");
        String generatedPassword = PasswordGenerator.generatePassword();
        signUpDTO.setPassword(generatedPassword);
      boolean save=  signUpRepo.save(signUpDTO);
      if(save){
          System.out.println("Sign Up data is saved successfully");
      }else {
          System.out.println("Data is not saved");
      }

        return true;
    }

    @Override
    public Optional<SignUpDTO> validateEmail(String email) {
        System.out.println("Running validateEmail in Service");
        long count = signUpRepo.countByEmail(email);
        if (count >= 1) {
            return Optional.of(new SignUpDTO());
        }
        return Optional.empty();
    }

    @Override
    public Optional<SignUpDTO> validatePhone(Long phone) {
        System.out.println("Running validatePhone in Service");
        long count= signUpRepo.countByPhone(phone);
        if(count>=1){
            return Optional.of(new SignUpDTO());
        }
        return Optional.empty();
    }
}
