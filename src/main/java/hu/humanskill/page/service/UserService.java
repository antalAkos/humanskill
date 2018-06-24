package hu.humanskill.page.service;

import hu.humanskill.page.Utility;
import hu.humanskill.page.model.Admin;
import hu.humanskill.page.model.Apply;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserService {

    private EntityManager em;
    private Utility utility;

    public UserService(EntityManager entityManager, Utility utility) {
        em = entityManager;
        this.utility = utility;
    }

    public List<Apply> getAll() {

        TypedQuery<Apply> q2 =
                em.createNamedQuery("getAllApplies", Apply.class);
        return q2.getResultList();
    }

    public void createApply() {
        Apply def = new Apply("name", "email", "666", "/files/upload/ujdomborzatterkep7684779104980445018.pdf");
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(def);
        transaction.commit();
    }

    public Admin getUserByName(String name) {
        TypedQuery<Admin> query = em.createNamedQuery(
                "getUserByName", Admin.class);
        return query.setParameter("name", name).getSingleResult();
    }


    public boolean checkIfUserNameExists(String name) {
        try{
            TypedQuery<Admin> query = em.createNamedQuery(
                    "getUserByName", Admin.class);
            System.out.println(query.setParameter("name", name).getSingleResult());
        } catch (NoResultException e){
            return false;
        }
        return true;
    }

    public void saveAdmin(String name, String pw) {

        Admin user = new Admin(name, pw);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(user);
        transaction.commit();
    }


    public Admin getUserPasswordByName(String name) {
        if (checkIfUserNameExists(name)) {
            TypedQuery<Admin> q2 =
                    em.createNamedQuery("getUserByName", Admin.class);
            return q2.setParameter("name", name).getSingleResult();
        } else {
            return null;
        }
    }

    public boolean createAdmin(String name, String password) {
        if (!checkIfUserNameExists(name)) {
            saveAdmin(
                    name,
                    utility.hashPassword(password)
                  );
            return true;
        }
        return false;
    }

    public boolean canLogIn(String name, String password) {
        return getUserPasswordByName(name) != null &&
                utility.checkPassword(password, getUserPasswordByName(name).getPassword());
    }

}
