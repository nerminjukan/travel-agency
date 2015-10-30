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
    void creat(Reservation r);
    void update(Reservation r);
    void remove(Reservation r);
    List<Reservation> findAll();
    Reservation findById(Long id);
    List<Reservation> findByCustomer(Customer customer);
    List<Reservation> findByTrip(Trip trip);
}
