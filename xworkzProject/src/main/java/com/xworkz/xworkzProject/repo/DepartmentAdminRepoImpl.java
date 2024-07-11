package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
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
    public boolean updateComplaintDepartmentAndStatus(Long id, Long departmentId, String status) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();

            // Fetch the complaint entity by id
            ComplaintsDTO complaint = entityManager.find(ComplaintsDTO.class, id);
            if (complaint == null) {
                return false; // Handle case where complaint with given id doesn't exist
            }

            // Update department and status
            DepartmentDTO department = entityManager.find(DepartmentDTO.class, departmentId);
            if (department == null) {
                return false; // Handle case where department with given id doesn't exist
            }



            entityManager.merge(complaint);
            entityTransaction.commit();
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            entityTransaction.rollback();
            return false;
        } finally {
            entityManager.close();
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


}
