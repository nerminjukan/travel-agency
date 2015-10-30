/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.entity.Customer;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jan Duda
 */
@Repository
public class ReservationDaoImpl implements ReservationDao{

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void creat(Reservation r) {
        em.persist(r);
    }

    @Override
    public void update(Reservation r) {
        em.merge(r);
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
    public List<Reservation> findByCustomer(Customer customer) {
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
            return em.createQuery("SELECT r FROM Reservation r WHERE r.customer.id = :tripId", Reservation.class)
                    .setParameter("tripId", trip.getId()).getResultList();
        } catch (NoResultException nre) {
            return null;
	}
    }
    
}
