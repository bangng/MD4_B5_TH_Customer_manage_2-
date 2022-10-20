package rikkei.academy.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rikkei.academy.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@Transactional
public class CustomerServiceIMPL implements ICustomerService {
    public static SessionFactory sessionFactory;
    public static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.conf.xml").buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Customer> findAll() {
        String selectAll = "SELECT ctm FROM Customer AS ctm";
        TypedQuery<Customer> query = entityManager.createQuery(selectAll, Customer.class);
        return query.getResultList();
    }
    @Override
    public Customer findById(Long id) {
        String deTailQuery = "SELECT C FROM Customer AS C WHERE C.id = :id";
        TypedQuery<Customer> query = entityManager.createQuery(deTailQuery, Customer.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
    @Override
    public void save(Customer customer) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (customer.getId() != 0) {
                Customer customer1 = findById(customer.getId());
                customer1.setName(customer.getName());
                customer1.setAddress(customer.getAddress());
            }
            session.saveOrUpdate(customer);
            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    @Override
    public void deleteById(Long id) {
        Session session1 = null;
        Transaction transaction1 = null;
        try {
            session1 = sessionFactory.openSession();
            transaction1 = session1.beginTransaction();
            session1.delete(findById(id));
            transaction1.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction1 != null) {
                transaction1.rollback();
            }
        } finally {
            if (session1 != null) {
                session1.close();
            }
        }
    }

    @Override
    public List<Customer> search() {
        return null;
    }
}
