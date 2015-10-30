package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.entity.Excursion;

import java.util.List;

/**
 * @author Ondrej Glasnak
 * date: 28.10.2015
 */
public interface ExcursionDao {
    void create(Excursion e);
    Excursion update(Excursion e);
    void remove(Excursion e);
    List<Excursion> findAll();
    Excursion findById(Long id);
    List<Excursion> findByName(String name);
    List<Excursion> findByDestination(String destination);
}