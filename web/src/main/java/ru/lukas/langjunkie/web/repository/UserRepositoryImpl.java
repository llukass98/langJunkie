/*
package ru.lukas.langjunkie.web.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.lukas.langjunkie.web.model.User;

*/
/**
 * @author Dmitry Lukashevich
 *//*

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveUser(User user) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
    }
}
*/
