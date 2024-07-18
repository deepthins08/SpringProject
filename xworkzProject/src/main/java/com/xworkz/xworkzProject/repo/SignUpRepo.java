package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.EmployeeDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;

import java.util.Optional;

public interface SignUpRepo {

    boolean save(SignUpDTO signUpDTO);

    long countByEmail(String email);

    long countByPhone(Long phone);

  Optional<SignUpDTO> findById(int id);
  SignUpDTO merge(SignUpDTO signUpDTO);
    Optional<SignUpDTO> findByEmail(String email);

    void updateFailedAttempts(int userId, int failedAttempts);

    void lockAccount(int userId);

    Optional<SignUpDTO> findByPhone(Long phone);

   SignUpDTO findByEmployeeId(int id);



}
