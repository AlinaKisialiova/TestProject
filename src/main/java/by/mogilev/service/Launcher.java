package by.mogilev.service;

import by.mogilev.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by akiseleva on 09.03.2015.
 */
public class Launcher {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("trainingCenter");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        User user = new User();
        entityManager.persist(user);

        entityTransaction.commit();

        User checkEntity = entityManager.find(User.class, user.getId());

        System.out.println("User " + checkEntity.getId());

        entityManager.close();
        entityManagerFactory.close();
    }

}
