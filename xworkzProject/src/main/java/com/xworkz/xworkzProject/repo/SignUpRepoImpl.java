package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Optional;

@Repository
public class SignUpRepoImpl implements SignUpRepo{
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    SignUpRepoImpl(){
        System.out.println("Created SignUpRepoImpl");
    }

    @Override
    public boolean save(SignUpDTO signUpDTO) {
        System.out.println("Running save method in SignUpRepoImpl");
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();
       EntityTransaction entityTransaction= entityManager.getTransaction();
       try{
           entityTransaction.begin();
           entityManager.persist(signUpDTO);
           entityTransaction.commit();
       }catch (PersistenceException e){
           e.printStackTrace();
           entityTransaction.rollback();
       }finally {
           entityManager.close();
       }
        return true;
    }

    @Override
    public long countByEmail(String email) {
        System.out.println("Running countByEmail in Repo");
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createNamedQuery("mail");
            query.setParameter("email", email);
            return (long) query.getSingleResult();
        } catch (PersistenceException e) {
            e.printStackTrace();
            return 0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public long countByPhone(Long phone) {
        System.out.println("Running countByPhone in Repo");
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createNamedQuery("phone");
            query.setParameter("phone", phone);
            return (long) query.getSingleResult();
        } catch (PersistenceException e) {
            e.printStackTrace();
            return 0;
        } finally {
            entityManager.close();
        }
    }
}
