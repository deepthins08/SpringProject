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

    @Override
    public Optional<SignUpDTO> findById(int id) {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();
        try {
            SignUpDTO signUpDto = entityManager.find(SignUpDTO.class, id);
            return Optional.ofNullable(signUpDto);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public SignUpDTO merge(SignUpDTO signUpDto) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            SignUpDTO mergedSignUpDto = entityManager.merge(signUpDto);
            entityTransaction.commit();
            return mergedSignUpDto;
        } catch (Exception e) {
            System.err.println("Error message"+e.getMessage());
            System.err.println("Error Couse"+e.getCause());

            e.printStackTrace();
            entityTransaction.rollback();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<SignUpDTO> findByEmail(String email) {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();
        try {
            TypedQuery<SignUpDTO> query = entityManager.createQuery("SELECT s FROM SignUpDTO s WHERE s.email = :email", SignUpDTO.class);
            query.setParameter("email", email);
            SignUpDTO result = query.getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    @Override
    public void updateFailedAttempts(int userId, int failedAttempts) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            SignUpDTO signUpDto = entityManager.find(SignUpDTO.class, userId);
            if (signUpDto != null) {
                signUpDto.setFailedAttempts(failedAttempts);
                entityManager.merge(signUpDto);
            }
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void lockAccount(int userId) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            SignUpDTO signUpDto = entityManager.find(SignUpDTO.class, userId);
            if (signUpDto != null) {
                signUpDto.setAccountLocked(true);
                entityManager.merge(signUpDto);
            }
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }



}
