package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class DepartmentAdminRepoImpl implements DepartmentAdminRepo{

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<DepartmentAdminDTO> findByEmail(String email) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("select d from DepartmentAdminDTO d where d.email=:email");
            query.setParameter("email", email);
            DepartmentAdminDTO departmentAdminDTO = (DepartmentAdminDTO) query.getSingleResult();
            return Optional.ofNullable(departmentAdminDTO);
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            log.error("{} ",e.getCause());
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

    @Override
    public boolean save(EmployeeDTO employeeDTO) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.persist(employeeDTO);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
        return true;
    }

    @Override
    public List<ComplaintsDTO> getAllComplaints() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<ComplaintsDTO> query = entityManager.createQuery("SELECT s FROM ComplaintsDTO s ORDER BY s.createdAt DESC", ComplaintsDTO.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
       EntityManager entityManager= this.entityManagerFactory.createEntityManager();
       try{
        TypedQuery<EmployeeDTO> query= entityManager.createQuery("select s from EmployeeDTO s", EmployeeDTO.class);
        return query.getResultList();
       }finally {
           entityManager.close();
       }
    }

    @Override
    public void updateComplaintDepartmentAndStatus(int id, Integer employeeId, String status) {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();

        ComplaintsDTO existingComplaint = entityManager.find(ComplaintsDTO.class, id);
        if (existingComplaint != null) {
            existingComplaint.setEmployeeId(employeeId);
            existingComplaint.setStatus(status);
            entityManager.merge(existingComplaint);
        } else {
            log.error("Complaint with ID {} not found for update", id);
            // Handle error or throw exception as needed
        }
    }



    @Override
    public List<DepartmentDTO> getAllDepartments() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<DepartmentDTO> query = entityManager.createQuery("SELECT d FROM DepartmentDTO d", DepartmentDTO.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<EmployeeDTO> findByDepartmentId(int departmentId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String jpql = "SELECT e FROM EmployeeDTO e WHERE e.departmentId = :departmentId";
            return entityManager.createQuery(jpql, EmployeeDTO.class)
                    .setParameter("departmentId", departmentId)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<DepartmentDTO> findByDepartmentName(String departmentName) {
        EntityManager entityManager=this.entityManagerFactory.createEntityManager();
        try{
         Query query = entityManager.createQuery("select s from DepartmentDTO s where departmentName=:departmentName");
          query.setParameter("departmentName",departmentName);
         Optional<DepartmentDTO> list=(Optional<DepartmentDTO>) query.getSingleResult();
         return list;
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return Optional.empty();

        }finally {
            entityManager.close();
        }

    }


}
