package com.example.demo112.repositories;

import com.example.demo112.models.ProductImage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ProductImageRepository {
    private SessionFactory sessionFactory;

    public ProductImageRepository() {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }
    public ProductImage save(ProductImage productImage) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(productImage);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productImage;
    }

    public List<ProductImage> findByProductId(Long productId) {
        Session session = sessionFactory.openSession();

        String hql = "FROM ProductImage WHERE product.id = :productId";
        Query<ProductImage> query = session.createQuery(hql, ProductImage.class);
        query.setParameter("productId", productId);
        return query.list();
    }

}