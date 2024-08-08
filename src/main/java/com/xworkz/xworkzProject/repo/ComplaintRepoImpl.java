package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.NotificationsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@Slf4j
public class ComplaintRepoImpl implements ComplaintRepo{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public boolean save(ComplaintsDTO complaintsDTO) {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();
       EntityTransaction entityTransaction= entityManager.getTransaction();
        complaintsDTO.setStatus("active");
        complaintsDTO.setRead(false);


//        complaintsDTO.setDepartmentId(0);
       try{
           entityTransaction.begin();
           entityManager.persist(complaintsDTO);
           entityTransaction.commit();

       }catch(PersistenceException e){
           e.printStackTrace();
           entityTransaction.rollback();
       }finally {
           entityManager.close();
       }
        return true;
    }


    @Override
    public List<ComplaintsDTO> findByUserIdAndStatus(int userId, String status) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            Query query= entityManager.createQuery("select c FROM ComplaintsDTO c WHERE c.userId = :userId AND c.status = :status", ComplaintsDTO.class);
            query.setParameter("userId", userId);
            query.setParameter("status", status);
            List<ComplaintsDTO> list=   query.getResultList();
            return list;
        } finally {
            entityManager.close();
        }
    }


    @Override
    public ComplaintsDTO findById(int id) {  // New method implementation
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
         Query  query= entityManager.createQuery("select s from ComplaintsDTO s where s.id=:id");
         query.setParameter("id",id);
       ComplaintsDTO complaintsDTO= (ComplaintsDTO) query.getSingleResult();
            return complaintsDTO;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean update(ComplaintsDTO complaintsDTO) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        complaintsDTO.setDepartmentRead(false);
        complaintsDTO.setEmployeeRead(false);
        complaintsDTO.setUserRead(false);
        try {
            entityTransaction.begin();

            // for updating status for admin
            ComplaintsDTO managedComplaint = entityManager.find(ComplaintsDTO.class, complaintsDTO.getId());
            if (managedComplaint != null) {

                managedComplaint.setStatus(complaintsDTO.getStatus());
                managedComplaint.setDepartmentId(complaintsDTO.getDepartmentId());
                managedComplaint.setEmployeeId(complaintsDTO.getEmployeeId());
                managedComplaint.setDescription(complaintsDTO.getDescription());
                managedComplaint.setComments(complaintsDTO.getComments());
                managedComplaint.setOtp(complaintsDTO.getOtp());
                managedComplaint.setModifiedAt(complaintsDTO.getModifiedAt());
                managedComplaint.setModifiedBy(complaintsDTO.getModifiedBy());
                managedComplaint.setRead(complaintsDTO.getRead());
                managedComplaint.setDepartmentRead(complaintsDTO.getDepartmentRead());
                managedComplaint.setEmployeeRead(complaintsDTO.getEmployeeRead());
                managedComplaint.setUserRead(complaintsDTO.getUserRead());

                entityManager.merge(managedComplaint); // Merge changes into the managed entity
            }

            entityTransaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            entityTransaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
        return true;
    }


    @Override
    public List<ComplaintsDTO> findByDepartmentName(String departmentName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("select c from ComplaintsDTO c where c.departmentId IN" +
                    " (select d.id from DepartmentDTO d where d.departmentName = :departmentName)");
            query.setParameter("departmentName", departmentName);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<ComplaintsDTO> findByUserId(int userId) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT c FROM ComplaintsDTO c WHERE c.userId = :userId", ComplaintsDTO.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<ComplaintsDTO> findAll() {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();

        return entityManager.createQuery("SELECT n FROM ComplaintsDTO n", ComplaintsDTO.class).getResultList();
    }


    @Override

    public List<ComplaintsDTO> findUnreadNotifications() {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();

        TypedQuery<ComplaintsDTO> query = entityManager.createQuery(
                "SELECT c FROM ComplaintsDTO c WHERE c.read = false", ComplaintsDTO.class);
        return query.getResultList();
    }

    @Override
    public void markNotificationAsRead(int id) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
       EntityTransaction entityTransaction= entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.createQuery("UPDATE ComplaintsDTO c SET c.read = true WHERE c.id = :complaintId")
                    .setParameter("complaintId", id)
                    .executeUpdate();
            entityTransaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            entityTransaction.rollback();
        }finally {
            entityManager.close();
        }
    }


    @Override
    public List<ComplaintsDTO> findUnread() {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT c FROM ComplaintsDTO c WHERE c.read = false", ComplaintsDTO.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }



    }


