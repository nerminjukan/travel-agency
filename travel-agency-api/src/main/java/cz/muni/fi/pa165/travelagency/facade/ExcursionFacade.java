package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.dto.ExcursionCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import java.util.List;

/**
 * Facade layer interface for Excursion entity
 * 
 * @author omular
 */
public interface ExcursionFacade {
    /**
     * Create new excursion.
     *
     * @param e DTO of new excursion
     * @return Id of newly created excursion.
     */
    public Long createExcursion(ExcursionCreateDTO e);

    /**
     * Get all excursion.
     *
     * @return List of DTO of all excursions.
     */
    public List<ExcursionDTO> getAllExcursions();

    /**
     * Get excursion by specified id.
     *
     * @param excursionId Id of requested excursion.
     * @return DTO of excursion with requested id.
     */
    public ExcursionDTO getExcursionById(Long excursionId);

    /**
     * Get all excursions reserved by user with specified id.
     *
     * @param userId Id of user.
     * @return List of all user's excursions.
     */
    public List<ExcursionDTO> getExcursionsByUser(Long userId);

    /**
     * Delete excursion with specified id.
     *
     * @param excursionId Id of excursion which should be removed.
     */
    public void deleteExcursion(Long excursionId);

    /**
     * Update excursion.
     *
     * @param e DTO of updated excursion.
     */
    public void updateExcursion(ExcursionDTO e);
}
