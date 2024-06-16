package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.AdminDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;

import java.util.List;
import java.util.Optional;

public interface AdminRepo {

  Optional<AdminDTO> findByEmail(String email);

  List<SignUpDTO> getAllUsers();
}
