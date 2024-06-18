package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.SignUpDTO;

import com.xworkz.xworkzProject.repo.SignUpRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService {


    @Autowired
    private SignUpRepo signUpRepo;

    @Autowired
    private JavaMailSender emailSender;

    SignUpServiceImpl() {
        System.out.println("Created SignUpServiceImpl");
    }

    @Override
    public boolean ValidateAndSave(SignUpDTO signUpDTO) {
        System.out.println("running save in SignUpServiceImpl");
        String generatedPassword = generateRandomPassword();
        signUpDTO.setPassword(generatedPassword);

        sendPassword(signUpDTO);

        String fullName = signUpDTO.getFirstName() + " " + signUpDTO.getLastName();
        signUpDTO.setCreatedBy(fullName);

        signUpDTO.setCreatedAt(LocalDateTime.now());

        signUpDTO.setCount(0);



        boolean save = signUpRepo.save(signUpDTO);
        if (save) {
            System.out.println("Sign Up data is saved successfully");
        } else {
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
        long count = signUpRepo.countByPhone(phone);
        if (count >= 1) {
            return Optional.of(new SignUpDTO());
        }
        return Optional.empty();
    }


    public void sendPassword(SignUpDTO signUpDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(signUpDTO.getEmail());
        message.setSubject("One Time Password");
        message.setText("Dear " + signUpDTO.getFirstName() + " " + signUpDTO.getLastName() + ", You have been successfully Signed Up,\n\n" +
                "Please Sign in through this password: " + signUpDTO.getPassword() + "\n\n" +
                "Thanks and Regards,\n" + " " +
                "XworkzProject Team");
        emailSender.send(message);
    }

    @Override
    public void updateLoginCount(int userId) {
        Optional<SignUpDTO> userOptional = signUpRepo.findById(userId);
        System.out.println("user Id" + userOptional);
        if (userOptional.isPresent()) {
            SignUpDTO user = userOptional.get();
            System.out.println("user" + user);
            user.setCount(user.getCount() + 1);
            signUpRepo.merge(user);
        }
    }

    public String generateRandomPassword() {
        int length = 16; // desired password length
        String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterSet.length());
            password.append(characterSet.charAt(index));
        }

        return password.toString();
    }

    @Override
    public Optional<SignUpDTO> findByEmail(String email) {
        System.out.println("Running findByEmail in Service");

        return signUpRepo.findByEmail(email);
    }

    @Override
    public SignUpDTO savePassword(SignUpDTO signUpDTO) {
        System.out.println("savepassword running");
        String systemUserName = System.getProperty("user.name");
        System.out.println("System Username: " + systemUserName);

        signUpDTO.setModifiedBy(systemUserName); // Set modifiedBy to the same as createdBy
        signUpDTO.setModifiedAt(LocalDateTime.now());
        return signUpRepo.merge(signUpDTO);
    }



    @Override
    public void incrementFailedAttempts(int userId) {
        Optional<SignUpDTO> userOptional = signUpRepo.findById(userId);
        if (userOptional.isPresent()) {
            SignUpDTO user = userOptional.get();
            user.setFailedAttempts(user.getFailedAttempts() + 1);
            signUpRepo.merge(user);
        }
    }

    @Override
    public void resetFailedAttempts(int userId) {
        Optional<SignUpDTO> userOptional = signUpRepo.findById(userId);
        if (userOptional.isPresent()) {
            SignUpDTO user = userOptional.get();
            user.setFailedAttempts(0);
            signUpRepo.merge(user);
        }
    }

    @Override
    public void lockAccount(int userId) {
        Optional<SignUpDTO> userOptional = signUpRepo.findById(userId);
        if (userOptional.isPresent()) {
            SignUpDTO user = userOptional.get();
            user.setAccountLocked(true);
            signUpRepo.merge(user);
        }
    }
}
