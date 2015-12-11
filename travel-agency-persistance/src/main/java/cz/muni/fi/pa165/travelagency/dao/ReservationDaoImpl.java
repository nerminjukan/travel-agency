package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.entity.User;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jan Duda
 */
@Transactional
@Repository
public class ReservationDaoImpl implements ReservationDao{

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(Reservation r) {
        em.persist(r);
    }

    @Override
    public Reservation update(Reservation r) {
        return em.merge(r);
    }

    @Override
    public void remove(Reservation r) {
        em.remove(r);
    }

    @Override
    public List<Reservation> findAll() {
        return em.createQuery("SELECT r FROM Reservation r", Reservation.class)
                .getResultList();
    }

    @Override
    public Reservation findById(Long id) {
        return em.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findByCustomer(User customer) {
        if(customer == null){
            throw new IllegalArgumentException("Customer is null!");
        }
        try{
            return em.createQuery("SELECT r FROM Reservation r WHERE r.customer.id = :customerId", Reservation.class)
                    .setParameter("customerId", customer.getId()).getResultList();
        } catch (NoResultException nre) {
            return null;
	}
    }

    @Override
    public List<Reservation> findByTrip(Trip trip) {
        if(trip == null){
            throw new IllegalArgumentException("Trip is null!");
        }
        
        try{
            return em.createQuery("SELECT r FROM Reservation r WHERE r.trip.id = :tripId", Reservation.class)
                    .setParameter("tripId", trip.getId()).getResultList();
        } catch (NoResultException nre) {
            return null;
	}
    }
    
}
