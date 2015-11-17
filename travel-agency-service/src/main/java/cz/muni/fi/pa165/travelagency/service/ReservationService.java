package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.entity.Customer;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import java.util.List;

public interface ReservationService {
    /**
     * Finds reservation by specified id
     *
     * @param id id of reservation to find
     * @return Reservation with specified id. Null if reservation with specified
     * id doesn't exist.
     */
    public Reservation findById(Long id);

    /**
     * Finds reservations of specified customer
     *
     * @param c customer
     * @return list of customer's reservations
     */
    public List<Reservation> findByCustomer(Customer c);
}
