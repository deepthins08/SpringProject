package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class EditInfoRepoImpl implements EditInfoRepo{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public SignUpDTO updateProfile(SignUpDTO signUpDTO) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
       EntityTransaction entityTransaction= entityManager.getTransaction();
        try {
            entityTransaction.begin();
            SignUpDTO updatedDTO = entityManager.merge(signUpDTO);
            entityTransaction.commit();
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
