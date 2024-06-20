package com.xworkz.xworkzProject.controller;

import com.xworkz.xworkzProject.dto.ImageDownloadDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import com.xworkz.xworkzProject.service.EditInfoService;
import com.xworkz.xworkzProject.service.ImageService;
import com.xworkz.xworkzProject.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class FileUploadController {

    private static final String UPLOAD_DIR = "C:\\Users\\Anil\\Desktop\\ImageUpload\\";
    @Autowired
    private EditInfoService editInfoService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private SignUpService signUpService;

    public FileUploadController() {
        System.out.println("Created FileUploadController");
    }

    @PostMapping("/editProfile")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile,
                             SignUpDTO signUpDTO,
                             Model model, HttpSession session) {
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

            Optional<SignUpDTO> optionalUser = signUpService.findByEmail(signUpDTO.getEmail());
            if (!optionalUser.isPresent()) {
                model.addAttribute("message", "User not found.");
                return "ProfileEdit";
            }

            SignUpDTO user = optionalUser.get();

            Optional<ImageDownloadDTO> existingImageOptional = imageService.getImageDetailsByUserId(user.getId());
            ImageDownloadDTO imageDTO;
            if (existingImageOptional.isPresent()) {
                imageDTO = existingImageOptional.get();
                // Update existing image details
                imageDTO.setImageName(newFilename);
                imageDTO.setImageType(multipartFile.getContentType());
                imageDTO.setImageSize(multipartFile.getSize());
                imageDTO.setModifiedAt(LocalDateTime.now());
                imageDTO.setModifiedBy(signUpDTO.getEmail());
                imageDTO.setStatus("Active");
                imageService.updateImageDetails(imageDTO);
            } else {
                // Save new image details
                imageDTO = new ImageDownloadDTO();
                imageDTO.setImageName(newFilename);
                imageDTO.setImageType(multipartFile.getContentType());
                imageDTO.setImageSize(multipartFile.getSize());
                imageDTO.setUserId(user.getId());
                imageDTO.setCreatedAt(LocalDateTime.now());
                imageDTO.setCreatedBy(signUpDTO.getEmail());
                imageDTO.setModifiedAt(LocalDateTime.now());
                imageDTO.setModifiedBy(signUpDTO.getEmail());
                imageDTO.setStatus("Active");
                imageService.saveImageDetails(imageDTO);
            }

            user.setProfileImage(newFilename);
            signUpService.saveUser(user);


                         signUpDTO.setProfileImage(newFilename);
            SignUpDTO updatedDTO = editInfoService.updateUserProfile(signUpDTO.getEmail(), signUpDTO);
            if (updatedDTO != null) {
                model.addAttribute("message", "Profile updated successfully!");
            } else {
                model.addAttribute("message", "Profile update failed. User not found.");
            }

//          set session for image to display
            String imageUrl = "/images/" + newFilename;
            session.setAttribute("profileImage", imageUrl);

//          model.addAttribute("imageURL", "/images/" + newFilename);
            model.addAttribute("dto", signUpDTO);
            model.addAttribute("image", imageDTO);

        } catch (IOException e) {
            model.addAttribute("message", "File upload failed: " + e.getMessage());
        }
        return "ProfileEdit";
    }


}
