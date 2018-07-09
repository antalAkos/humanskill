package hu.humanskill.page.service;

import hu.humanskill.page.model.Apply;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.File;

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

    public void remove(Long id) {
        Query query = entityManager.createNamedQuery("findById", Apply.class);
        query.setParameter("id", id);
        Apply toRemove = (Apply)query.setParameter("id", id).getSingleResult();
        File fileToRemove = new File(toRemove.getFilename());
        fileToRemove.delete();
        entityManager.remove(toRemove);

    }

}
