package com.example.demo112.repositories;

import com.example.demo112.models.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Field;
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

    public Order update(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(order);
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

    public Page<Order> findByKeyword(String keyword, PageRequest pageRequest) {
        Session session = sessionFactory.openSession();

        // Create the base query
        String queryString = "FROM Order o WHERE "
                + "(o.active = true AND (:keyword IS NULL OR :keyword = '' OR "
                + "o.fullName LIKE CONCAT('%', :keyword, '%') "
                + "OR o.address LIKE CONCAT('%', :keyword, '%') "
                + "OR o.note LIKE CONCAT('%', :keyword, '%') "
                + "OR o.email LIKE CONCAT('%', :keyword, '%')))";
        Query<Order> query = session.createQuery(queryString, Order.class);
        query.setParameter("keyword", keyword);
        query.setFirstResult((pageRequest.getPageNumber()) * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        // Execute the query to get the results
        List<Order> orders = query.getResultList();

        // Count total results
        String countQueryString = "SELECT COUNT(o) " + queryString;
        Query<Long> countQuery = session.createQuery(countQueryString, Long.class);
        countQuery.setParameter("keyword", keyword);
        Long totalElements = countQuery.getSingleResult();

        // Create a Page instance with the results and pagination information
        return new PageImpl<>(orders, pageRequest, totalElements);
    }
}