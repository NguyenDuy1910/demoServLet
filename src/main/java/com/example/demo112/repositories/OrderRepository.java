package com.example.demo112.repositories;

import com.example.demo112.models.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class OrderRepository {
    private SessionFactory sessionFactory;

    public OrderRepository() {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public Order findById(Long orderId) {
        Session session = sessionFactory.openSession();

        try {
            return session.get(Order.class, orderId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }
    public Order save(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return order;
    }

    public List<Order> findByUserId(Long userId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Order> orders = null;

        try {
            transaction = session.beginTransaction();

            String hql = "FROM Order o WHERE o.user.id = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);

            orders = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return orders;
    }
}