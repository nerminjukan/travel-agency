package cz.muni.fi.pa165.travelagency.rest.controllers;

import cz.muni.fi.pa165.travelagency.dto.TripCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.dto.TripUpdateDTO;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.rest.ApiUris;
import cz.muni.fi.pa165.travelagency.rest.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.travelagency.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.travelagency.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ondrej Glasnak
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_TRIPS)
public class TripsController
{
    final static Logger logger = LoggerFactory.getLogger(TripsController.class);

    @Autowired
    private TripFacade tripFacade;

    /**
     * get all trips
     *
     * @return list of all TripDTO
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDTO> getTrips() {
        logger.debug("rest getTrips()");
        return tripFacade.getAllTrips();
    }

    /**
     * Returns trip according to id
     *
     * @param id id of trip required.
     * @return created trip
     * @throws Exception ResourceNotFoundException if no such trip exists.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TripDTO getTrip(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getTrip({id})", id);

        try{
            TripDTO tripDTO = tripFacade.getTripById(id);
            return tripDTO;
        } catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }

    /**
     * creates new trip
     *
     * @param trip TripCreateDTO filled with data needed for creating new Trip
     * @return created trip (TripDTO)
     * @throws Exception ResourceAlreadyExistingException if trip already exists.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final TripDTO createTrip(@RequestBody TripCreateDTO trip) throws Exception {
        logger.debug("rest createTrip()");
        try{
            Long id = tripFacade.createTrip(trip);
            return tripFacade.getTripById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    /**
     * Updates trip with new values
     *
     * @param trip TripUpdateDTO filled with data needed for updating Trip
     * @return updated trip (TripDTO)
     * @throws Exception InvalidParameterException if params are invalid.
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final TripDTO updateTrip(@RequestBody TripUpdateDTO trip) throws Exception {
        logger.debug("rest updateTrip()", trip.getId());
        try{
            tripFacade.updateTrip(trip);
            return tripFacade.getTripById(trip.getId());
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    /**
     * Deletes trip of this ID
     *
     * @param id ID of Trip
     * @throws Exception ResourceNotFoundException when trip is not found.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteTrip(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteTrip({})", id);
        try{
            tripFacade.deleteTrip(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

}
