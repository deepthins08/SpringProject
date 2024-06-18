package com.xworkz.xworkzProject.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.EditInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileUploadController {

    @Autowired
    private EditInfoService editInfoService;

    private static final String UPLOAD_DIR = "C:\\Users\\Anil\\Desktop\\ImageUpload\\";

    public FileUploadController() {
        System.out.println("Created FileUploadController");
    }

    @PostMapping("/editProfile")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile,
                            SignUpDTO signUpDTO,
                             Model model) {
        if (multipartFile.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "ProfileEdit";
        }
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String newFilename = signUpDTO.getEmail() + "_" + originalFilename;
            Path path = Paths.get(UPLOAD_DIR, newFilename);
            Files.write(path, multipartFile.getBytes());
            signUpDTO.setProfileImage(newFilename);
            SignUpDTO updatedDTO = editInfoService.updateUserProfile(signUpDTO.getEmail(), signUpDTO);
            if (updatedDTO != null) {
                model.addAttribute("message", "Profile updated successfully!");
            } else {
                model.addAttribute("message", "Profile update failed. User not found.");
            }

            model.addAttribute("dto", signUpDTO);

        } catch (IOException e) {
            model.addAttribute("message", "File upload failed: " + e.getMessage());
        }
        return "ProfileEdit";
    }
}
