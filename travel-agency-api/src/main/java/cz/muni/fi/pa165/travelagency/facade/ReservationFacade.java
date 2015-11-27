package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationTotalPriceDTO;
import java.math.BigDecimal;
import java.util.List;

/**
 * Facade layer for Reservation entity
 * 
 * @author Jan Duda
 */
public interface ReservationFacade {
    
    /**
     * Method creates new reservation
     * 
     * @param r reservationDTO to be created
     */
    void createReservation(ReservationDTO r);
    
    /**
     * Method updates reservation from input parameter
     * 
     * @param r reservationDTO to be updated
     */
    void updateReservation(ReservationDTO r);
    
    /**
     * Method deletes reservation from input parameter
     * 
     * @param r reservationDTO to be removed
     */
    void removeReservation(ReservationDTO r);
    
    /**
     * Method returns all reservations
     * 
     * @return list of all reservations
     */
    List<ReservationDTO> getAllReservation();
    
    /**
     * Method returns reservation with Id which is specified in input
     * 
     * @param reservationId Id of reservation
     * @return reservation with Id which is specified in input
     */
    ReservationDTO getReservationById(Long reservationId);
    
    /**
     * Method returns list of reservation of customer with Id from input
     * 
     * @param customerId Id of customer
     * @return all reservation of customer
     */
    List<ReservationDTO> getReservationByCustomer(Long customerId);

    /**
     * Return total
     *
     * @param reservationId
     * @return
     */
    ReservationTotalPriceDTO getTotalPriceOfReservation(Long reservationId);
}
