package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.AdminDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;



@Repository
public class AdminRepoImpl implements AdminRepo {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<AdminDTO> findByEmail(String email) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createNamedQuery("findByMail");
            query.setParameter("email", email);
            AdminDTO adminDTO = (AdminDTO) query.getSingleResult();
            return Optional.ofNullable(adminDTO);
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

    @Override
    public List<SignUpDTO> getAllUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<SignUpDTO> query = entityManager.createQuery("SELECT s FROM SignUpDTO s", SignUpDTO.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
}
