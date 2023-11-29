package com.example.demo112.repositories;

import com.example.demo112.models.Category;
import com.example.demo112.models.User;
import com.example.demo112.utility.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CategoryRepository {
    private static final SessionFactory SESSION_FACTORY = HibernateUtility.getSessionFactory();

    public Category findById(Long id) {
        Session session = SESSION_FACTORY.openSession();
        try {
            // Sử dụng session để truy vấn Category theo id
            return session.get(Category.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public Category save(Category category) {
        try (Session session = SESSION_FACTORY.openSession()) {
            try {
                session.beginTransaction();
                session.save(category);
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                System.err.println("Error occurred while saving the user: " + e.getMessage());
            }
        }
        return category;
    }

    public List<Category> findAll() {
        try (Session session = SESSION_FACTORY.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
            Root<Category> root = criteriaQuery.from(Category.class);
            criteriaQuery.select(root);

            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteById(long id) {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();

            Category category = session.get(Category.class, id);
            if (category != null) {
                session.delete(category);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}