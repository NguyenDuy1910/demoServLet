package com.example.demo112.repositories;

import com.example.demo112.models.Role;
import com.example.demo112.models.User;
import com.example.demo112.utility.HibernateUtility;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RoleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Role findById(Long Roleid) {
        Session session = HibernateUtility.getSessionFactory().openSession();

        try {
            return session.get(Role.class, Roleid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }
}
