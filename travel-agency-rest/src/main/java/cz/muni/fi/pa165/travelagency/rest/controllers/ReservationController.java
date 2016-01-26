/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.rest.controllers;

import cz.muni.fi.pa165.travelagency.dto.ReservationCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationUpdateDTO;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
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
 * REST controller for Reservations
 * 
 * @author Radovan Sinko
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_RESERVATIONS)
public class ReservationController {
    
    final static Logger logger = LoggerFactory.getLogger(ReservationController.class);
    
    @Autowired
    private ReservationFacade reservationFacade;
    
    /**
     * Returns all reservations
     * 
     * @return list of reservationsDTOs
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ReservationDTO> getReservations(){
        logger.debug("rest getReservations()");
        return reservationFacade.getAllReservations();
    }
    
    /**
     * Returns reservation according to id
     * 
     * @param id reservation id
     * @return ReservationDTO
     * @throws Exception ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ReservationDTO getReservation(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getReservation with id ({id})", id);
        
        try{
            ReservationDTO reservationDTO = reservationFacade.getReservationById(id);
            return reservationDTO;
        } catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }
    
    /**
     * Deletes reservation according to id
     * 
     * @param id reservation id
     * @throws Exception ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteReservation(@PathVariable("id") long id) throws Exception{
        logger.debug("rest deleteReservation({})", id);
        try{
            reservationFacade.removeReservation(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }
    
    /**
     * Creates a new reservation by POST method
     * 
     * @param reservation ReservationCreateDTO with required fields for creation
     * @return the created reservation
     * @throws Exception ResourceAlreadyExistingException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ReservationDTO createReservation(@RequestBody ReservationCreateDTO reservation) throws Exception{
        logger.debug("rest createReservation()");
        try{
            Long id = reservationFacade.createReservation(reservation, null);
            return reservationFacade.getReservationById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }
    
    /**
     * Update reservation
     * 
     * @param reservation ReservationUpdateDTO with required fields for update
     * @return the updated reservation
     * @throws Exception InvalidParameterException
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ReservationDTO updateReservation(@RequestBody ReservationUpdateDTO reservation) throws Exception{
        logger.debug("rest updateReservation with id ({})", reservation.getId());
        try{
            reservationFacade.updateReservation(reservation);
            return reservationFacade.getReservationById(reservation.getId());
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }
}