package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.dto.TripDTO;

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
     */
    void createTrip(TripDTO t);

    /**
     * deletes trip
     *
     * @param t trip to be deleted
     */
    void deleteTrip(Long tripId);

    /**
     * updates trip with information specified in param t
     *
     * @param t trip DTO with updated information
     */
    void updateTrip(TripDTO t);

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

}
