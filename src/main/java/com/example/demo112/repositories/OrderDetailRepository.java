package com.example.demo112.repositories;

import com.example.demo112.models.OrderDetail;
import com.example.demo112.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

//import javax.persistence.Query;
import java.util.List;

public class OrderDetailRepository {
    private SessionFactory sessionFactory;

    public OrderDetailRepository() {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }
    public void saveAll(List<OrderDetail> orderDetails) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            for (OrderDetail orderDetail : orderDetails) {
                session.save(orderDetail);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public OrderDetail save(OrderDetail orderDetail) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.save(orderDetail);
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                System.err.println("Error occurred while saving the user: " + e.getMessage());
            }
        }
        return orderDetail;
    }

    public List<OrderDetail> findByOrderId(Long orderId) {
        // Sử dụng Hibernate để truy vấn CSDL và lấy danh sách OrderDetail dựa trên orderId
        // Ví dụ:
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<OrderDetail> orderDetails = null;
        try {
            transaction = session.beginTransaction();

            String hql = "FROM OrderDetail od WHERE od.order.id = :orderId";
            Query<OrderDetail> query = session.createQuery(hql);
            query.setParameter("orderId", orderId);

            orderDetails = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return orderDetails;
    }
}
