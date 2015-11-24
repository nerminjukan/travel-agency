package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Trip;

import java.util.List;

/**
 * @author Ondrej Glasnak, Ondrej Mular
 * date 23/11/15
 */
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
    void deleteTrip(Trip t);
}
