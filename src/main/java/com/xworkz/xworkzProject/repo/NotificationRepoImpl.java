package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.NotificationsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class NotificationRepoImpl implements NotificationRepo{

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Override

    public void save(NotificationsDTO notification) {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();
      EntityTransaction entityTransaction= entityManager.getTransaction();
      try{
          entityTransaction.begin();
          entityManager.persist(notification);
          entityTransaction.commit();
      }catch (PersistenceException e){
          e.printStackTrace();
      }finally {
          entityManager.close();
      }
    }

    @Override

    public List<NotificationsDTO> findAll() {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();

        return entityManager.createQuery("SELECT n FROM NotificationsDTO n", NotificationsDTO.class).getResultList();
    }

    @Override

    public int countUnread() {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();

        Long count = entityManager.createQuery("SELECT COUNT(n) FROM NotificationsDTO n WHERE n.read = false", Long.class).getSingleResult();
        return count.intValue();
    }

    @Override

    public void markNotificationAsRead(int id) {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();
        NotificationsDTO notification = entityManager.find(NotificationsDTO.class, id);
        if (notification != null) {
            notification.setRead(true);
            entityManager.merge(notification);
        }
    }

    @Override
    public NotificationsDTO findById(int id) {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();

        return entityManager.find(NotificationsDTO.class, id);
    }

    @Override
    public Optional<NotificationsDTO> findByComplaintId(Integer complaintId) {
       EntityManager entityManager= this.entityManagerFactory.createEntityManager();
       try {
           Query query=entityManager.createQuery("select s from NotificationsDTO s where s.complaintId=:complaintId");
           query.setParameter("complaintId",complaintId);
          return (Optional<NotificationsDTO>) query.getSingleResult();

       }catch (Exception e){
           e.printStackTrace();
       }finally {
           entityManager.close();
       }

        return Optional.empty();
    }
}
