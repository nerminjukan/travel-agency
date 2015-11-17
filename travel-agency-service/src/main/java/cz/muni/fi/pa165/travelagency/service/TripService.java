package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Trip;

public interface TripService {
    /**
     * Finds trip by specified id
     *
     * @param id id of trip to find
     * @return trip with specified id. Null if trip with specified id doesn't
     * exist.
     */
    public Trip findById(Long id);

    /**
     * Add excursion to trip
     *
     * @param t trip to which excursion will be added
     * @param e excursion which will be added to specified trip
     */
    public void addExcursionToTrip(Trip t, Excursion e);
}
