package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.entity.Excursion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Ondrej Glasnak
 * date    28.10.2015
 */
@Repository
public class ExcursionDaoImpl implements ExcursionDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Excursion e) {
        em.persist(e);
    }

    @Override
    public void update(Excursion e) {
        em.merge(e);
    }

    @Override
    public void remove(Excursion e) {
        em.remove(e);
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
    public List<Excursion> findByName(String name) {
        return em.createQuery(
                "SELECT e FROM Excursion e WHERE e.name LIKE :name ",
                Excursion.class).setParameter(
                "name", "%" + name + "%").getResultList();
    }

    @Override
    public List<Excursion> findByDestination(String destination) {
        return em.createQuery(
                "SELECT e FROM Excursion e WHERE e.destination LIKE :dest ",
                Excursion.class
        ).setParameter("dest", "%" + destination + "%").getResultList();
    }
}
