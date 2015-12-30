package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.entity.Excursion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.transaction.annotation.Transactional;

/**
 * implementated methods of the ExcursionDao interface
 * @author Ondrej Glasnak date 28.10.2015
 */
@Repository
@Transactional
public class ExcursionDaoImpl implements ExcursionDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void create(Excursion e) {
        em.persist(e);
    }

    @Override
    public Excursion update(Excursion e) {
        return em.merge(e);
    }

    @Override
    public void remove(Excursion e) throws IllegalArgumentException {
        em.remove(findById(e.getId()));
    }

    @Override
    public List<Excursion> findAll() {
        return em.createQuery("SELECT e FROM Excursion e", Excursion.class)
                .getResultList();
    }

    @Override
    public Excursion findById(Long id) {
        return em.find(Excursion.class, id);
    }

    @Override
    public Excursion findByName(String name) {
        try {
            return em.createQuery(
                    "SELECT e FROM Excursion e WHERE e.name = :name ",
                    Excursion.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public List<Excursion> findByDestination(String destination) {
        return em.createQuery(
                "SELECT e FROM Excursion e WHERE e.destination LIKE :dest ",
                Excursion.class
        ).setParameter("dest", "%" + destination + "%").getResultList();
    }
}
