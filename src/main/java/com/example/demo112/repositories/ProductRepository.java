package com.example.demo112.repositories;

import com.example.demo112.models.Product;
import com.example.demo112.utility.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


import java.util.List;


public class ProductRepository {

    private static final SessionFactory SESSION_FACTORY = HibernateUtility.getSessionFactory();

    public Product addProduct(Product product) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(product);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
                System.err.println("Error occurred while saving the product: " + e.getMessage());
                throw e;
            }
        }
        return product;
    }

    public Product findProductById(Long productId) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Product product = session.get(Product.class, productId);
                transaction.commit();
                return product;
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
                throw e;
            }
        }
    }

    public List<Product> findAll(int pageNumber, int pageSize) {
        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "FROM Product";
            Query<Product> query = session.createQuery(hql, Product.class);
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Page<Product> searchProducts(Long categoryId, String keyword, PageRequest pageRequest) {
        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "FROM Product p WHERE (:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
                    "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE :keyword OR p.description LIKE :keyword)";
            Query<Product> query = session.createQuery(hql, Product.class);
            query.setParameter("categoryId", categoryId);
            query.setParameter("keyword", "%" + keyword + "%");
            query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
            query.setMaxResults(pageRequest.getPageSize());

            List<Product> productsList = query.list();
            long totalResults = getTotalResults(categoryId, keyword);
            return new PageImpl<>(productsList, pageRequest, totalResults);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private long getTotalResults(Long categoryId, String keyword) {
        try (Session session = SESSION_FACTORY.openSession()) {
            String countHql = "SELECT COUNT(p) FROM Product p WHERE (:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
                    "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE :keyword OR p.description LIKE :keyword)";
            Query<Long> countQuery = session.createQuery(countHql, Long.class);
            countQuery.setParameter("categoryId", categoryId);
            countQuery.setParameter("keyword", "%" + keyword + "%");
            return countQuery.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public void close() {
        SESSION_FACTORY.close();
    }
}