package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.ComplaintsDTO;
import com.xworkz.xworkzProject.dto.DepartmentAdminDTO;
import com.xworkz.xworkzProject.dto.EmployeeDTO;
import com.xworkz.xworkzProject.dto.SignUpDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class EmployeeRepoImpl implements EmployeeRepo {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<EmployeeDTO> findByEmail(String email) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            TypedQuery<EmployeeDTO> query = entityManager.createQuery("SELECT e FROM EmployeeDTO e WHERE e.email = :email", EmployeeDTO.class);
            query.setParameter("email", email);
            EmployeeDTO employeeDTO = query.getSingleResult();
            return Optional.ofNullable(employeeDTO);
        } catch (NoResultException e) {
            return Optional.empty(); // Return empty optional if no result found
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<ComplaintsDTO> findAssignedComplaintsByEmployeeId(int employeeId) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        String jpql = "SELECT c FROM ComplaintsDTO c WHERE c.employeeId = :employeeId";
        TypedQuery<ComplaintsDTO> query = entityManager.createQuery(jpql, ComplaintsDTO.class);
        query.setParameter("employeeId", employeeId);
        return query.getResultList();
    }

    @Override
    public void updateComplaintStatus(int id, String status) {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();

        ComplaintsDTO existingComplaint = entityManager.find(ComplaintsDTO.class, id);
        if (existingComplaint != null) {

            existingComplaint.setStatus(status);
            entityManager.merge(existingComplaint);
        } else {
            log.error("Complaint with ID {} not found for update", id);
            // Handle error or throw exception as needed
        }
    }

    @Override
    public Optional<EmployeeDTO> findByPhone(Long phone) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            TypedQuery<EmployeeDTO> query = entityManager.createQuery("SELECT s FROM EmployeeDTO s WHERE s.phone = :phone", EmployeeDTO.class);
            query.setParameter("phone", phone);
            EmployeeDTO result = query.getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public long countByEmail(String email) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(e) FROM EmployeeDTO e WHERE e.email = :email");
            query.setParameter("email", email);
            return (long) query.getSingleResult();
        } catch (PersistenceException e) {
            e.printStackTrace(); // Handle exception appropriately
            return 0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public long countByPhone(Long phone) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(e) FROM EmployeeDTO e WHERE e.phone = :phone");
            query.setParameter("phone", phone);
            return (long) query.getSingleResult();
        } catch (PersistenceException e) {
            e.printStackTrace(); // Handle exception appropriately
            return 0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void save(EmployeeDTO employeeDTO) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            if (employeeDTO.getId() == 0) {
                entityManager.persist(employeeDTO); // For new entities
            } else {
                entityManager.merge(employeeDTO); // For existing entities
            }
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace(); // Handle exception appropriately
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void updateOtp(String email, String password) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Query query = entityManager.createQuery("UPDATE EmployeeDTO e SET e.password = :password WHERE e.email = :email");
            query.setParameter("password", password);
            query.setParameter("email", email);
            query.executeUpdate();
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<EmployeeDTO> findAll() {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        String query = "SELECT e FROM EmployeeDTO e";
        return entityManager.createQuery(query, EmployeeDTO.class).getResultList();
    }

    @Override
    public void markNotificationAsRead(int id) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction= entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.createQuery("UPDATE ComplaintsDTO c SET c.employeeRead = true WHERE c.id = :complaintId")
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
            Query query = entityManager.createQuery("SELECT c FROM ComplaintsDTO c WHERE c.employeeRead = false", ComplaintsDTO.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

}
