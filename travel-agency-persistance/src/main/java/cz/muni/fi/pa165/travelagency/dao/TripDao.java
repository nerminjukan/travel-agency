package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.entity.Trip;

import java.util.List;

/**
 * This interface provides persistent operation for entity Trip
 *
 * @author Ondrej Mular
 */
public interface TripDao {
    /**
     * Create Trip in DB
     * 
     * @param t Trip object to add into DB
     */
    void create(Trip t);
    
    /**
     * Update Trip in DB
     * 
     * @param t Trip to be updated
     * @return updated Trip
     */
    Trip update(Trip t);
    
    /**
     * Remove Trip from DB
     * 
     * @param t Trip to be removed
     */
    void remove(Trip t);
    
    /**
     * Find all Trip from DB
     * 
     * @return list of all trips
     */
    List<Trip> findAll();
    
    /**
     * Find in DB Trip entity with specified id
     * 
     * @param id id of entity
     * @return Trip with specified id, null if there is no entity with that id
     */
    Trip findById(Long id);
    
    /**
     * In DB find all Trip entities witch has in name string {@code name} 
     * 
     * @param name string to find in name
     * @return list of Trip entities with string {@code name} in name
     */
    List<Trip> findByName(String name);
    
    /**
     * In DB find all Trip entities witch has in destination string {@code destination} 
     * 
     * @param destination string to find in destination
     * @return list of Trip entities with string {@code destination} in destination
     */
    List<Trip> findByDestintion(String destination);
}
