package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.ImageDownloadDTO;

import java.util.Optional;

public interface ImageRepo {

    void saveImageDetails(ImageDownloadDTO imageDTO);

    Optional<ImageDownloadDTO> findByUserId(int userId);

    void deactivateAllImagesForUser(int userId);




}
