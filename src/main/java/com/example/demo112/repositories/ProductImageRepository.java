package com.example.demo112.repositories;

import com.example.demo112.models.ProductImage;
import com.example.demo112.utility.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProductImageRepository {
    private static final SessionFactory SESSION_FACTORY = HibernateUtility.getSessionFactory();

    public ProductImage save(ProductImage productImage) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(productImage);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
        return productImage;
    }

    public List<ProductImage> findByProductId(Long productId) {
        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "FROM ProductImage WHERE product.id = :productId";
            Query<ProductImage> query = session.createQuery(hql, ProductImage.class);
            query.setParameter("productId", productId);
            return query.list();
        }
    }
}