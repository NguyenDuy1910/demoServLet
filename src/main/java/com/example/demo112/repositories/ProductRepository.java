package com.example.demo112.repositories;

import com.example.demo112.models.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ProductRepository {

    private SessionFactory sessionFactory;

    public ProductRepository() {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public Product addProduct(Product product) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return product;
    }

    public Product findProductById(Long productId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Product product = null;
        try {
            transaction = session.beginTransaction();
            product = session.get(Product.class, productId);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return product;
    }

    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}