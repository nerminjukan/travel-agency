package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserDaoImpl implements {@link UserDao}.
 *
 * @author Radovan Sinko
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void create(User c) {
        em.persist(c);
    }

    @Override
    public User update(User c) {
        return em.merge(c);
    }

    @Override
    public void remove(User c) {
        em.remove(c);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT c FROM User c", User.class).getResultList();
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findByName(String name) {
        return em.createQuery("SELECT c FROM User c WHERE c.name LIKE :name ",
                User.class).setParameter("name", "%" + name + "%").getResultList();
    }

    @Override
    public User findByEmail(String email) {
        try {
            return em.createQuery("SELECT c FROM User c WHERE c.email = :email ",
                    User.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }
}
