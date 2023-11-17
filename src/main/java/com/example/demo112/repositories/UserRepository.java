package com.example.demo112.repositories;

import com.example.demo112.models.User;
import com.example.demo112.utility.HibernateUtility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class UserRepository {
    private static final SessionFactory SESSION_FACTORY = HibernateUtility.getSessionFactory();

    public User save(User user) {
        try (Session session = SESSION_FACTORY.openSession()) {
            try {
                session.beginTransaction();
                session.save(user);
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                System.err.println("Error occurred while saving the user: " + e.getMessage());
            }
        }
        return user;
    }

    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "FROM User WHERE phoneNumber = :phoneNumber";
            return session.createQuery(hql, User.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .uniqueResultOptional();
        }
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "SELECT COUNT(*) FROM User WHERE phoneNumber = :phoneNumber";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .uniqueResult();
            return count > 0L;
        }
    }

    public User findById(Long userId) {
        Session session = SESSION_FACTORY.openSession();
        try {
            return session.get(User.class, userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    // Add other repository methods as needed
}