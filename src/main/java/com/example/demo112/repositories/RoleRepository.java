package com.example.demo112.repositories;

import com.example.demo112.models.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.example.demo112.utility.HibernateUtility;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

public class RoleRepository {


    private final SessionFactory sessionFactory;

    public RoleRepository() {
        this.sessionFactory = HibernateUtility.getSessionFactory();
    }

    public Optional<Role> findById(Long roleId) {
        Session session = sessionFactory.openSession();

        try {

            return Optional.ofNullable(session.get(Role.class, roleId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}