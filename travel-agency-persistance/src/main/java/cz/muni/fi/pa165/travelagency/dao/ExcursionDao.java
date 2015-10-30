package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.entity.Excursion;

import java.util.List;

/**
 * interface to data layer and persistent operations for entity Excursion
 * @author Ondrej Glasnak
 * date: 28.10.2015
 */
public interface ExcursionDao {
    /**
     * Create new excursion e in the database
     * @param e new excrusion
     */
    void create(Excursion e);
    /**
     * update existing excursion in the database
     * @param e updated excursion
     * @return updated excursion
     */
    Excursion update(Excursion e);
    /**
     * remove excursion from the database
     * @param e excursion to be removed
     */
    void remove(Excursion e);
    /**
     * list all excursions in the database
     * @return list of all existing excursions in a database
     */
    List<Excursion> findAll();
    /**
     * find excursion with provided Id.
     * @param id id of the excursion to be found
     * @return single excursion with unique Id.
     */
    Excursion findById(Long id);
    /**
     * find excursions with given substring in the name
     * @param name substring of the name of excursion to be found.
     * @return all excursion with given substring in the name.
     */
    Excursion findByName(String name);
    /**
     * find all excursions with given destination.
     * @param destination
     * @return all excursions with given destination.
     */
    List<Excursion> findByDestination(String destination);
}
