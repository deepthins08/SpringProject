package com.xworkz.xworkzProject.repo;

import com.xworkz.xworkzProject.dto.HistoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

@Repository
@Slf4j
public class HistoryRepoImpl implements HistoryRepo{

    @Autowired
   private EntityManagerFactory entityManagerFactory;

    public HistoryRepoImpl(){
        System.out.println("Creating HistoryRepoImpl");
    }


    @Override
    public void save(HistoryDTO historyDTO) {
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();
       EntityTransaction entityTransaction= entityManager.getTransaction();
       try{
           entityTransaction.begin();
           entityManager.persist(historyDTO);
           log.info("Saved history with ID:{} " , historyDTO.getHistoryId());
           entityTransaction.commit();

       }catch (PersistenceException e){
           e.printStackTrace();
           entityTransaction.rollback();
       }finally {
           entityManager.close();
       }
    }
}
