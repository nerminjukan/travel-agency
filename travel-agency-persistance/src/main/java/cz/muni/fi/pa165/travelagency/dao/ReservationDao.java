package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.entity.User;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import java.util.List;

/**
 * The interface provides persistent operation for entity Reservation
 * 
 * @author Jan Duda
 */
public interface ReservationDao {
    /**
     * Method adds new a reservation into database
     * 
     * @param r reservation to add
     */
    void create(Reservation r);
    
    /**
     * Method updates a reservation r in database
     * 
     * @param r reservation to update
     * @return updated reservation
     */
    Reservation update(Reservation r);
    
    /**
     * Method removes a reservation r from database
     * 
     * @param r 
     */
    void remove(Reservation r);
    
    /**
     * Method returns all reservations from database
     * 
     * @return all reservations in database
     */
    List<Reservation> findAll();
    
    /**
     * Method returns the reservation with input id
     * 
     * @param id reservation will be returned with this id
     * @return reservation with input id
     */
    Reservation findById(Long id);
    
    /**
     * Method returns all reservations with input user
     * 
     * @param user all reservations will be returned with this user
     * @return all reservations with input user
     */
    List<Reservation> findByUser(User user);
    
    /**
     * Method returns all reservations with input trip
     * 
     * @param trip all reservations will be returned with this trip
     * @return all reservations with input trip
     */
    List<Reservation> findByTrip(Trip trip);
}
