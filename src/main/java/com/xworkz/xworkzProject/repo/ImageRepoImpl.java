package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.ImageDownloadDTO;
import com.xworkz.xworkzProject.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
@Slf4j
public class ImageRepoImpl implements ImageRepo {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    ImageRepoImpl(){
        System.out.println("Created ImageRepoImpl");
    }

    public void saveImageDetails(ImageDownloadDTO imageDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(imageDTO);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<ImageDownloadDTO> findByUserId(int userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<ImageDownloadDTO> query = entityManager.createQuery(
                    "SELECT i FROM ImageDownloadDTO i WHERE i.userId = :userId", ImageDownloadDTO.class);
            query.setParameter("userId", userId);
            return query.getResultList().stream().findFirst();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deactivateAllImagesForUser(int userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("UPDATE ImageDownloadDTO i SET i.status = 'No' WHERE i.userId = :userId");
            query.setParameter("userId", userId);
            query.executeUpdate();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }


}
