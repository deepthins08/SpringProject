package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.SignUpDTO;

import java.util.Optional;

public interface SignUpRepo {

    boolean save(SignUpDTO signUpDTO);

    long countByEmail(String email);

    long countByPhone(Long phone);
}
