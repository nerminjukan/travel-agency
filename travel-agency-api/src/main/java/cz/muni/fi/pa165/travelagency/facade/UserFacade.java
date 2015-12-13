package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import java.util.List;

/**
 * Facade layer for Customer entity
 * 
 * @author Radovan Sinko
 */
public interface UserFacade {

    /**
     * Update customer
     * 
     * @param c DTO of customer to be updated
     */
    void updateUser(UserDTO c);
    
    /**
     * Remove customer
     * 
     * @param c DTO of customer to be removed
     */
    void removeUser(UserDTO c);

    /**
     * Finds and returns all customers
     * 
     * @return list of all customers
     */
    List<UserDTO> getAllUsers();
    
    /**
     * Finds and returns customer with specified id
     * 
     * @param customerId Customer id
     * @return customer with specified id
     */
    UserDTO getUserById(Long customerId);
    
    /**
     * Finds and returns list of customers with specified name
     * 
     * @param name Name of customer
     * @return list of customers with specified id
     */
    List<UserDTO> getUserByName(String name);
    
    /**
     * Finds and returns customer with specified email
     * 
     * @param email Email of customer
     * @return list of customers with specified email
     */
    UserDTO getUserByEmail(String email);

    /**
     * Check if user is admin
     *
     * @param u user
     * @return true if specified user is admin, false otherwise
     */
    boolean isUserAdmin(UserDTO u);

    /**
     * Authenticate user
     *
     * @param u user credentials
     * @return authenticated user DTO if correct email and password entered, null otherwise
     */
    UserDTO authUser(UserAuthenticateDTO u);
}
