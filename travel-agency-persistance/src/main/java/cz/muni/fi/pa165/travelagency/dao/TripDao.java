package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.entity.Trip;

import java.util.List;

/**
 *
 * @author Ondrej Mular
 */
public interface TripDao {
    void create(Trip t);
    void update(Trip t);
    void remove(Trip t);
    List<Trip> findAll();
    Trip findById(Long id);
    List<Trip> findByName(String name);
    List<Trip> findByDestintion(String destination);
}
