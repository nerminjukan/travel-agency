package cz.muni.fi.pa165.travelagency.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.muni.fi.pa165.travelagency.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.rest.ApiUris;
import cz.muni.fi.pa165.travelagency.rest.exceptions.ResourceNotFoundException;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Users
 *
 * @author omular
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_USERS)
public class UsersController {
    
    final static Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private ReservationFacade reservationFacade;

    /**
     * returns all users
     *
     * @return list of UserDTOs
     * @throws JsonProcessingException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<UserDTO> getUsers() throws JsonProcessingException {
        logger.debug("rest getUsers()");
        return userFacade.getAllUsers();
    }

    /**
     *
     * getting user according to id
     *
     * @param id user identifier
     * @return UserDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO getUser(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getUser({})", id);
        UserDTO userDTO = userFacade.getUserById(id);
        if (userDTO == null) {
            throw new ResourceNotFoundException();
        }
        return userDTO;
    }

    @RequestMapping(value = "/{id}/reservations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<ReservationDTO> getUsersReservations(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getUsersReservations({})", id);
        UserDTO userDTO = userFacade.getUserById(id);
        if (userDTO == null) {
            throw new ResourceNotFoundException();
        }
        return reservationFacade.getReservationsByUser(id);
    }
}
