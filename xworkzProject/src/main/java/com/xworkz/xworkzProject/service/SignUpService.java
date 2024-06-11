package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.SignUpDTO;

import java.util.Optional;

public interface SignUpService {

    boolean ValidateAndSave(SignUpDTO signUpDTO);

    Optional<SignUpDTO> validateEmail(String email);
    Optional<SignUpDTO> validatePhone(Long phone);
}
