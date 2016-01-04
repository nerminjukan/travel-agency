package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import java.sql.Date;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author Ondrej Glasnak, Ondrej Mular
 * date 23/11/15
 */
@Service
public interface TripService {

    /**
     * Finds trip by specified id
     *
     * @param id id of trip to find
     * @return trip with specified id. Null if trip with specified id doesn't
     * exist.
     */
    Trip findById(Long id);

    /**
     * Add excursion to trip
     *
     * @param t trip to which excursion will be added
     * @param e excursion which will be added to specified trip
     */
    void addExcursionToTrip(Trip t, Excursion e);


    /**
     * Method finds the trip with specified name.
     * @return name of trip to find
     */
    Trip findByName(String name);

    /**
     * Method finds all trips.
     * @return list of all trips
     */
    List<Trip> findAll();

    /**
     * Method creates new trip.
     * @param t trip to be created
     * @return
     */
    Trip createTrip(Trip t);

    /**
     * Method updates information about the trip.
     * @param t trip to be updated
     * @return updated trip
     */
    Trip updateTrip(Trip t);

    /**
     * Method ... of the trip.
     * @param t
     */
    void removeTrip(Trip t);

    /**
     * Method returns number of available trips free for reservation
     *
     * @param t trip of which left free slots should be returned
     * @return number of free slots for reservation
     */
    Long getNumberOfAvailableTripsLeft(Trip t);

    /**
     * Get trips in date range.
     *
     * @param start starting date of date range
     * @param end ending date of date range
     * @return trips that are in specified date range
     */
    List<Trip> getTripsInDateRange(Date start, Date end);
    
    /**
     * Gets trip in which is excursion with excursionId.
     * 
     * @param excursionId id of excursion 
     * @return trip where is excursion
     */
    Trip getTripByExcursion(Long excursionId);
}
