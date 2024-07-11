package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.SignUpDTO;

import com.xworkz.xworkzProject.repo.SignUpRepo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
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
        log.info("running save in SignUpServiceImpl");
        String generatedPassword = generateRandomPassword();
        signUpDTO.setPassword(generatedPassword);

        sendPassword(signUpDTO);

        String fullName = signUpDTO.getFirstName() + " " + signUpDTO.getLastName();
        signUpDTO.setCreatedBy(fullName);

        signUpDTO.setCreatedAt(LocalDateTime.now());

        signUpDTO.setCount(0);



        boolean save = signUpRepo.save(signUpDTO);
        if (save) {
           log.info("Sign Up data is saved successfully");
        } else {
            log.info("Data is not saved");
        }

        return true;
    }

    @Override
    public Optional<SignUpDTO> validateEmail(String email) {
        log.info("Running validateEmail in Service");
        long count = signUpRepo.countByEmail(email);
        if (count >= 1) {
            return Optional.of(new SignUpDTO());
        }
        return Optional.empty();
    }

    @Override
    public Optional<SignUpDTO> validatePhone(Long phone) {
        log.info("Running validatePhone in Service");
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
        log.info("user Id{} " , userOptional);
        if (userOptional.isPresent()) {
            SignUpDTO user = userOptional.get();
           log.info("user" + user);
            user.setCount(user.getCount() + 1);
            signUpRepo.merge(user);
        }
    }

    // generating random password to send password through email
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
       log.info("Running findByEmail in Service");

        return signUpRepo.findByEmail(email);
    }

    @Override
    public SignUpDTO savePassword(SignUpDTO signUpDTO) {
       log.info("save password running");
        String systemUserName = System.getProperty("user.name");
        log.info("System Username:{} " , systemUserName);

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

    public void saveUser(SignUpDTO signUpDTO) {
        signUpRepo.save(signUpDTO);
    }

    @Override
    public Optional<SignUpDTO> findByPhone(Long phone) {
        log.info("Running findByEmail in Service");

        return signUpRepo.findByPhone(phone);
    }


    @Override
    public SignUpDTO findById(int id) {

       Optional<SignUpDTO> signUpDTO= signUpRepo.findById(id);
        return signUpDTO.get();
    }
}
