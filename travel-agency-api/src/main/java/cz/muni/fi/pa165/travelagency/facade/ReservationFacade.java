package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.dto.ReservationCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationTotalPriceDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
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
    Long createReservation(ReservationCreateDTO r, UserDTO user);
    
    /**
     * Method updates reservation from input parameter
     * 
     * @param r reservationDTO to be updated
     */
    void updateReservation(ReservationDTO r);
    
    /**
     * Method deletes reservation from input parameter
     * 
     * @param reservationId id of reservation to be removed
     */
    void removeReservation(Long reservationId);
    
    /**
     * Method returns all reservations
     * 
     * @return list of all reservations
     */
    List<ReservationDTO> getAllReservations();
    
    /**
     * Method returns reservation with Id which is specified in input
     * 
     * @param reservationId Id of reservation
     * @return reservation with Id which is specified in input
     */
    ReservationDTO getReservationById(Long reservationId);
    
    /**
     * Method returns list of reservation of user with Id from input
     * 
     * @param userId Id of user
     * @return all reservations of user
     */
    List<ReservationDTO> getReservationsByUser(Long userId);
    
    /**
     * Method returns list of reservation of trip with Id from input
     * 
     * @param tripId Id of trip
     * @return all reservations of trip
     */
    List<ReservationDTO> getReservationsByTrip(Long tripId);

    /**
     * Return total price of specified reservation
     *
     * @param reservationId Id of reservation to get it's total price
     * @return total price of specified reservation
     */
    ReservationTotalPriceDTO getTotalPriceOfReservation(Long reservationId);
}
