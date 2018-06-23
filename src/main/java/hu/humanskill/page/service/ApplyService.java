package hu.humanskill.page.service;

import hu.humanskill.page.model.Apply;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ApplyService {


    private EntityManager entityManager;

    public ApplyService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Apply apply) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(apply);
        transaction.commit();

    }
}
