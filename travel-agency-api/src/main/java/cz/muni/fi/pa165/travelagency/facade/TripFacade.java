package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.dto.TripCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.dto.TripUpdateDTO;

import java.util.List;

/**
 * Facade layer for Trip
 *
 * @author Ondrej Glasnak
 *         date 22/11/15
 */
public interface TripFacade {

    /**
     * Create new trip.
     *
     * @param t trip DTO to be created.
     * @return id of created trip
     */
    Long createTrip(TripCreateDTO t);

    /**
     * deletes trip
     *
     * @param tripId trip's id
     */
    void deleteTrip(Long tripId);

    /**
     * updates trip with information specified in param t
     *
     * @param t trip DTO with updated information
     */
    void updateTrip(TripUpdateDTO t);

    /**
     * get list of all trips
     *
     * @return list of all trips
     */
    List<TripDTO> getAllTrips();

    /**
     * finds trip with specified Id and returns it
     *
     * @param tripId id of trip to be found
     * @return trip with this id or null if none found.
     */
    TripDTO getTripById(Long tripId);

    /**
     * finds all trips reserved by the specified user
     *
     * @param userId id of user
     * @return list of trips
     */
    List<TripDTO> getTripsByUser(Long userId);
    
    /**
     * Finds trip in which is excursion with excursionId.
     * 
     * @param excursionId id of excursion 
     * @return trip where is excursion
     */
    TripDTO getTripByExcursion(Long excursionId);

}
