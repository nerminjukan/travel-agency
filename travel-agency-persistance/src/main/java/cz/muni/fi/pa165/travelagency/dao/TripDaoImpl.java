package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.entity.Trip;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 *
 * @author Ondrej Mular
 */
@Repository
public class TripDaoImpl implements TripDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(Trip t) {
        em.persist(t);
    }

    @Override
    public void update(Trip t) {
        em.merge(t);
    }

    @Override
    public void remove(Trip t) {
        em.remove(t);
    }

    @Override
    public List<Trip> findAll() {
        return em.createQuery("SELECT t FROM Trip t", Trip.class)
                .getResultList();
    }

    @Override
    public Trip findById(Long id) {
        return em.find(Trip.class, id);
    }

    @Override
    public List<Trip> findByName(String name) {
        return em.createQuery(
                "SELECT t FROM Trip t WHERE t.name LIKE :name ", Trip.class
        ).setParameter("name", "%" + name + "%").getResultList();
    }

    @Override
    public List<Trip> findByDestintion(String destination) {
        return em.createQuery(
                "SELECT t FROM Trip t WHERE t.destination LIKE :dest ",
                Trip.class
        ).setParameter("dest", "%" + destination + "%").getResultList();
    }
}
