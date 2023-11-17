package com.example.demo112.utility;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtility {
    private static SessionFactory sessionFactory;

    private HibernateUtility() {
    }

    public static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(com.example.demo112.models.User.class);
            configuration.addAnnotatedClass(com.example.demo112.models.Role.class);
            configuration.addAnnotatedClass(com.example.demo112.models.Order.class);
            configuration.addAnnotatedClass(com.example.demo112.models.Product.class);
            configuration.addAnnotatedClass(com.example.demo112.models.ProductImage.class);
            configuration.addAnnotatedClass(com.example.demo112.models.OrderDetail.class);
            configuration.addAnnotatedClass(com.example.demo112.models.Category.class);
            configuration.addAnnotatedClass(com.example.demo112.models.Token.class);


            // Create c3p0 ComboPooledDataSource
            ComboPooledDataSource dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(configuration.getProperty("hibernate.connection.driver_class"));
            dataSource.setJdbcUrl(configuration.getProperty("hibernate.connection.url"));
            dataSource.setUser(configuration.getProperty("hibernate.connection.username"));
            dataSource.setPassword(configuration.getProperty("hibernate.connection.password"));

            // Set c3p0 connection pool properties
            dataSource.setMinPoolSize(Integer.parseInt(configuration.getProperty("hibernate.c3p0.min_size")));
            dataSource.setMaxPoolSize(Integer.parseInt(configuration.getProperty("hibernate.c3p0.max_size")));
            dataSource.setAcquireIncrement(1);
            dataSource.setIdleConnectionTestPeriod(Integer.parseInt(configuration.getProperty("hibernate.c3p0.idle_test_period")));
            // ...

            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .applySetting("hibernate.connection.datasource", dataSource)
                    .build();

            return configuration.buildSessionFactory(registry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
}