package com.xworkz.xworkzProject.service;

import com.xworkz.xworkzProject.dto.ImageDownloadDTO;

import java.util.Optional;

public interface ImageService {

    void saveImageDetails(ImageDownloadDTO imageDTO);

    Optional<ImageDownloadDTO> getImageDetailsByUserId(int userId);

    void deactivateAllImagesForUser(int userId);

//    void updateImageDetails(ImageDownloadDTO imageDTO);


}
