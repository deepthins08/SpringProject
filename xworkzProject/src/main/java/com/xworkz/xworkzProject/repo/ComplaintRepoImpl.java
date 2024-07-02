package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class ComplaintRepoImpl implements ComplaintRepo{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public boolean save(ComplaintsDTO complaintsDTO) {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();
       EntityTransaction entityTransaction= entityManager.getTransaction();
        complaintsDTO.setStatus("active");
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
        try {
            entityTransaction.begin();

            // for updating status for admin
            ComplaintsDTO managedComplaint = entityManager.find(ComplaintsDTO.class, complaintsDTO.getId());
            if (managedComplaint != null) {
                managedComplaint.setStatus(complaintsDTO.getStatus());
                entityManager.merge(managedComplaint); // Merge changes into the managed entity
            }

            Query query = entityManager.createQuery("update ComplaintsDTO c set c.description=:description where c.id=:id");
            query.setParameter("description",complaintsDTO.getDescription());
            query.setParameter("id",complaintsDTO.getId());
            query.executeUpdate();
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

}
