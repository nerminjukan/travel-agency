package cz.muni.fi.pa165.travel_agency.dao;

import cz.muni.fi.pa165.travel_agency.entity.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * CustomerDaoImpl implements {@link CustomerDao}.
 * 
 * @author Radovan Sinko
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Customer c) {
        em.persist(c);
    }

    @Override
    public void update(Customer c) {
        em.merge(c);
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
        return em.createQuery("SELECT c FROM Customer c WHERE c.name = :name ",
                Customer.class).setParameter("name", name).getResultList();
    }

    @Override
    public List<Customer> findByEmail(String email) {
        return em.createQuery("SELECT c FROM Customer c WHERE c.email = :email ",
                Customer.class).setParameter("email", email).getResultList();
    }

}
