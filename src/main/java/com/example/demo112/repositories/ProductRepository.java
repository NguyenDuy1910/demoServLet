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


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


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

    public Page<Product> searchProducts(int categoryId, String keyword, PageRequest pageRequest) {
        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "FROM Product p WHERE (:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
                    "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE :keyword OR p.description LIKE :keyword)";
            Query<Product> query = session.createQuery(hql, Product.class);
            query.setParameter("categoryId", categoryId);
            query.setParameter("keyword", "%" + keyword + "%");
//            bắt đầu page từ 1
            query.setFirstResult((pageRequest.getPageNumber() - 1) * pageRequest.getPageSize());
            query.setMaxResults(pageRequest.getPageSize());

            List<Product> productsList = query.list();
            long totalResults = getTotalResults(categoryId, keyword, session);
//            long totalResults=3;

            return new PageImpl<>(productsList, pageRequest, totalResults);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private long getTotalResults(int categoryId, String keyword, Session session) {
        try {
            String countHql = "SELECT COUNT(p) FROM Product p WHERE (:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
                    "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE :keyword OR p.description LIKE :keyword)";
            Query<Long> countQuery = session.createQuery(countHql, Long.class);
            countQuery.setParameter("categoryId", categoryId);
            countQuery.setParameter("keyword", "%" + keyword + "%");
//            countQuery.setFirstResult((pageRequest.getPageNumber() - 1) * pageRequest.getPageSize());
            return countQuery.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<Product> getDetailProduct(Long productId) {
        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.id = :productId";
            Query<Product> query = session.createQuery(hql, Product.class);
            query.setParameter("productId", productId);

            Product product = query.uniqueResult();
            return Optional.ofNullable(product);
        }
    }

    public Product updateProductThumbnail(long id, String thumbnailUrl) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();

            Product product = session.get(Product.class, id);
            if (product != null) {
                product.setThumbnail(thumbnailUrl);
                session.update(product);
                transaction.commit();

            }
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý các ngoại lệ xảy ra
            throw new RuntimeException("not update thumbnail.", e);
        }
    }

    public Product updateProduct(Product updatedProduct) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();

            Product existingProduct = session.get(Product.class, updatedProduct.getId());
            if (existingProduct != null) {
                // Update the fields of the existing product with the values from the updated product
                existingProduct.setName(updatedProduct.getName());
                existingProduct.setPrice(updatedProduct.getPrice());
                // Update other fields as needed

                session.update(existingProduct);
                transaction.commit();
            }
            return existingProduct;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that occur
            throw new RuntimeException("Failed to update product.", e);
        }
    }

    public List<Product> findProductsByIds(List<Long> productIds) {
        try (Session session = SESSION_FACTORY.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.select(root).where(root.get("id").in(productIds));
            Query<Product> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý các ngoại lệ xảy ra
            throw new RuntimeException("Không thể lấy danh sách sản phẩm theo IDs.", e);
        }
    }

    public void deleteProduct(long id) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();

            Product product = session.get(Product.class, id);
            if (product != null) {
                session.delete(product);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete product.", e);
        }
    }

    public void close() {
        SESSION_FACTORY.close();
    }
}