package cz.muni.fi.pa165.travelagency.rest.controllers;

import cz.muni.fi.pa165.travelagency.dto.ExcursionCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.dto.ExcursionUpdateDTO;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.rest.ApiUris;
import cz.muni.fi.pa165.travelagency.rest.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.travelagency.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.travelagency.rest.exceptions.ResourceNotFoundException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for excursions
 * 
 * @author Jan Duda
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_EXCURSIONS)
public class ExcursionsController {
    
    final static Logger logger = LoggerFactory.getLogger(ExcursionsController.class);
    
    @Autowired
    private ExcursionFacade excursionFacade;
    
    /**
     * Returns all excursions
     * 
     * @return list of excursionDTOs
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ExcursionDTO> getExcursions(){
        logger.debug("rest getReservations()");
        return excursionFacade.getAllExcursions();
    }
    
    /**
     * Returns excursion according to id
     * 
     * @param id excursion's id
     * @return ExcursionDTO
     * @throws Exception ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ExcursionDTO getExcursion(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getExcursion({id})", id);
        
        try{
            ExcursionDTO reservationDTO = excursionFacade.getExcursionById(id);
            return reservationDTO;
        } catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }
    
    /**
     * Deletes excursion according to id
     * 
     * @param id excursion's id
     * @throws Exception ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteExcursion(@PathVariable("id") long id) throws Exception{
        logger.debug("rest deleteExcursion({})", id);
        try{
            excursionFacade.deleteExcursion(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }
    
    /**
     * Creates a new excursion by POST method
     * 
     * @param excursion ExcursionCreateDTO with required fields for creation
     * @return the created excursion
     * @throws Exception ResourceAlreadyExistingException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ExcursionDTO createExcursion(@RequestBody ExcursionCreateDTO excursion) throws Exception{
        logger.debug("rest createExcursion()");
        try{
            Long id = excursionFacade.createExcursion(excursion);
            return excursionFacade.getExcursionById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }
    
    /**
     * Updates excursion
     * 
     * @param excursion ExcursionUpdateDTO with required fields for update
     * @return the updated excursion
     * @throws Exception InvalidParameterException
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ExcursionDTO updateExcursion(@RequestBody ExcursionUpdateDTO excursion) throws Exception{
        logger.debug("rest updateExcursion({})", excursion.getId());
        try{
            excursionFacade.updateExcursion(excursion);
            return excursionFacade.getExcursionById(excursion.getTripId());
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }
}
