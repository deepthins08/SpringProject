package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ImageDownloadDTO;
import com.xworkz.xworkzProject.repo.ImageRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepo imageRepo;

    ImageServiceImpl(){
        log.info("created ImageServiceImpl");
    }

    @Override
    public void saveImageDetails(ImageDownloadDTO imageDTO) {
        imageRepo.saveImageDetails(imageDTO);
    }



    @Override
    public Optional<ImageDownloadDTO> getImageDetailsByUserId(int userId) {
        return imageRepo.findByUserId(userId);
    }


    @Override
    public void deactivateAllImagesForUser(int userId) {
        imageRepo.deactivateAllImagesForUser(userId);
    }


}
