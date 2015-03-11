package by.mogilev.service;

import by.mogilev.model.User;
import by.mogilev.model.UserRole;

import javax.persistence.*;

/**
* Created by akiseleva on 09.03.2015.
*/
public class Launcher {

    private static final String PERSISTENCE_UNIT_NAME = "center";
     static EntityManager em;
   private static EntityManagerFactory factory;

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = factory.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();

        entityTransaction.begin();

        User user = new User("user", "1", UserRole.ROLE_LECTOR);
        em.persist(user);

        entityTransaction.commit();

        User checkEntity = em.find(User.class, user.getId());

        System.out.println("User " + checkEntity.getId());
        em.flush();
       em.close();
       factory.close();
    }

}
