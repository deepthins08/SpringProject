package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class DepartmentAdminRepoImpl implements DepartmentAdminRepo{

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<DepartmentAdminDTO> findAdminByDepartmentId(Integer departmentId) {
      EntityManager entityManager=  this.entityManagerFactory.createEntityManager();
      try{
     Query query= entityManager.createQuery("select s from DepartmentAdminDTO s where departmentId=:departmentId");
     query.setParameter("departmentId",departmentId);
    DepartmentAdminDTO departmentAdminDTO1= (DepartmentAdminDTO) query.getSingleResult();
        return Optional.ofNullable(departmentAdminDTO1);
      }catch (Exception e){
          e.printStackTrace();
      }finally {
          entityManager.close();
      }
      return  Optional.empty();
    }

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
    public List<ComplaintsDTO> findComplaintsByDepartmentId(Integer departmentId) {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();

        String jpql = "SELECT c FROM ComplaintsDTO c WHERE c.departmentId = :departmentId";
        TypedQuery<ComplaintsDTO> query = entityManager.createQuery(jpql, ComplaintsDTO.class);
        query.setParameter("departmentId", departmentId);
        return query.getResultList();
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

    @Override
    public Optional<DepartmentAdminDTO> findById(int id) {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();
        try {
            DepartmentAdminDTO departmentAdminDTO = entityManager.find(DepartmentAdminDTO.class, id);
            return Optional.ofNullable(departmentAdminDTO);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public DepartmentAdminDTO merge(DepartmentAdminDTO departmentAdminDTO) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            DepartmentAdminDTO mergedDepartment = entityManager.merge(departmentAdminDTO);
            entityTransaction.commit();
            return mergedDepartment;
        } catch (Exception e) {
            log.error("Error message{} ",e.getMessage());
            log.error("Error Couse{} ",e.getCause());

            e.printStackTrace();
            entityTransaction.rollback();
            return null;
        } finally {
            entityManager.close();
        }
    }


    @Override
    public long countByEmail(String email) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(e) FROM DepartmentAdminDTO e WHERE e.email = :email");
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
            Query query = entityManager.createQuery("SELECT COUNT(e) FROM DepartmentAdminDTO e WHERE e.phone = :phone");
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
    public Optional<DepartmentAdminDTO> findByPhone(Long phone) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            TypedQuery<DepartmentAdminDTO> query = entityManager.createQuery("SELECT s FROM DepartmentAdminDTO s WHERE s.phone = :phone", DepartmentAdminDTO.class);
            query.setParameter("phone", phone);
            DepartmentAdminDTO result = query.getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    @Override
    public List<ComplaintsDTO> getNotificationsForAdmin(Integer departmentId) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        String sql = "SELECT c FROM ComplaintsDTO c WHERE departmentId = :departmentId AND status = 'assigned'"; // Adjust SQL based on your schema
        Query query = entityManager.createQuery(sql, ComplaintsDTO.class);
        query.setParameter("departmentId", departmentId);
        return query.getResultList();
    }


    @Override
    public void markNotificationAsRead(int id) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction= entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.createQuery("UPDATE ComplaintsDTO c SET c.departmentRead = true WHERE c.id = :complaintId")
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
            Query query = entityManager.createQuery("SELECT c FROM ComplaintsDTO c WHERE c.departmentRead = false", ComplaintsDTO.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

}
