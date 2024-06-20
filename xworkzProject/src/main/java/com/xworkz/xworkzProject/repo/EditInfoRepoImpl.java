package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class EditInfoRepoImpl implements EditInfoRepo{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public SignUpDTO updateProfile(SignUpDTO signUpDTO) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            SignUpDTO updatedDTO = entityManager.merge(signUpDTO);
            entityManager.getTransaction().commit();
            return updatedDTO;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public SignUpDTO findByEmail(String email) {
       EntityManager entityManager= this.entityManagerFactory.createEntityManager();
        try {
            TypedQuery<SignUpDTO> query = entityManager.createQuery(
                    "SELECT u FROM SignUpDTO u WHERE u.email = :email", SignUpDTO.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
