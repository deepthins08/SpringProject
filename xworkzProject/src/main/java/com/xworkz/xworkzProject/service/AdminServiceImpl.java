package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.AdminDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.repo.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Override
    public Optional<AdminDTO> findByEmail(String email) {

        Optional<AdminDTO> optional = adminRepo.findByEmail(email);
        if (optional.isPresent()) {
            return optional;
        } else {
            return Optional.empty();
        }
    }


    @Override
    public List<SignUpDTO> getAllUsers() {
        return adminRepo.getAllUsers();
    }
}


