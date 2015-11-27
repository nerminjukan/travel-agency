package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.entity.Excursion;
import java.util.List;

/**
 * Service layer interface for {@link cz.muni.fi.pa165.travelagency.entity.Excursion} entity.
 *
 * @author omular
 */
public interface ExcursionService {
    /**
     * Finds excursion by specified id.
     *
     * @param excursionId Id of requested excursion.
     * @return Requested excursion. Null if excursion with specified
     * id doesn't exist.
     */
    public Excursion findById(Long excursionId);

    /**
     * Returns all existing excursions.
     *
     * @return List of all excursions.
     */
    public List<Excursion> findAll();

    /**
     * Create new excursion
     *
     * @param e Excursion which should be created.
     * @return New created excursion.
     */
    public Excursion createExcursion(Excursion e);

    /**
     * Update existing excursion.
     * 
     * @param e Excursion which should be updated.
     * @return Updated excursion.
     */
    public Excursion updateExcursion(Excursion e);

    /**
     * Delete existing excursion.
     *
     * @param e Excursion which should be deleted.
     */
    public void removeExcursion(Excursion e);

    /**
     * Returns excursion with specified name
     *
     * @param name Name of excursion
     * @return Excursion with specified name. null if excursion with specified
     * name doesn't exist.
     */
    public Excursion findByName(String name);
}
