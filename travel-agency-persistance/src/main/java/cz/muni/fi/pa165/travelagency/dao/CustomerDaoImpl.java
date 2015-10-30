package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.entity.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * CustomerDaoImpl implements {@link CustomerDao}.
 *
 * @author Radovan Sinko
 */
@Transactional
@Repository
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void create(Customer c) {
        em.persist(c);
    }

    @Override
    public Customer update(Customer c) {
        return em.merge(c);
    }

    @Override
    public void remove(Customer c) {
        em.remove(c);
    }

    @Override
    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    @Override
    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    @Override
    public List<Customer> findByName(String name) {
        return em.createQuery("SELECT c FROM Customer c WHERE c.name LIKE :name ",
                Customer.class).setParameter("name", "%" + name + "%").getResultList();
    }

    @Override
    public Customer findByEmail(String email) {
        try {
            return em.createQuery("SELECT c FROM Customer c WHERE c.email = :email ",
                    Customer.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }
}
