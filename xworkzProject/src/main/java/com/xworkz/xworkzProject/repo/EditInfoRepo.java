package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;

public interface EditInfoRepo {

    SignUpDTO updateProfile(SignUpDTO signUpDTO);

    SignUpDTO findByEmail(String email);



}
