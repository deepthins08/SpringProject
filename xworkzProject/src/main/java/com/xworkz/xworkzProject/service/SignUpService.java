package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.SignUpDTO;

import java.util.Optional;

public interface SignUpService {

    boolean ValidateAndSave(SignUpDTO signUpDTO);

    Optional<SignUpDTO> validateEmail(String email);

    Optional<SignUpDTO> validatePhone(Long phone);

   void updateLoginCount(int userId);

    Optional<SignUpDTO> findByEmail(String email);

    SignUpDTO savePassword(SignUpDTO signUpDTO);

    void incrementFailedAttempts(int userId);

    void resetFailedAttempts(int userId);

    void lockAccount(int userId);

    void sendPassword(SignUpDTO signUpDTO,String decryptPassword);
   String generateRandomPassword();
    void saveUser(SignUpDTO signUpDTO);

    Optional<SignUpDTO> findByPhone(Long phone);

    SignUpDTO findById(int id);





}
