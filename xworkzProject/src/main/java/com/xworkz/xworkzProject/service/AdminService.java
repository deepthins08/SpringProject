package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.AdminDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    Optional<AdminDTO> findByEmail(String email);

    List<SignUpDTO> getAllUsers();

}
