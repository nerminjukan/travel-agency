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

/**
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
     */
    void update(Reservation r);
    
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
     * Method returns all reservations with input customer
     * 
     * @param customer all reservations will be returned with this customer
     * @return all reservations with input customer
     */
    List<Reservation> findByCustomer(Customer customer);
    
    /**
     * Method returns all reservations with input trip
     * 
     * @param trip all reservations will be returned with this trip
     * @return all reservations with input trip
     */
    List<Reservation> findByTrip(Trip trip);
}
