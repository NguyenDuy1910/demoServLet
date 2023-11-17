package com.example.demo112.repositories;

import com.example.demo112.models.Category;
import com.example.demo112.utility.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
}