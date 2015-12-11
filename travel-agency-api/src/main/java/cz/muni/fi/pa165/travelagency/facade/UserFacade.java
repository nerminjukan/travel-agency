package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import java.util.List;

/**
 * Facade layer for Customer entity
 * 
 * @author Radovan Sinko
 */
public interface UserFacade {

    /**
     * Create new customer
     * 
     * @param c DTO of customer to be created
     */
    void createCustomer(UserDTO c);

    /**
     * Update customer
     * 
     * @param c DTO of customer to be updated
     */
    void updateCustomer(UserDTO c);
    
    /**
     * Remove customer
     * 
     * @param c DTO of customer to be removed
     */
    void removeCustomer(UserDTO c);

    /**
     * Finds and returns all customers
     * 
     * @return list of all customers
     */
    List<UserDTO> getAllCustomers();
    
    /**
     * Finds and returns customer with specified id
     * 
     * @param customerId Customer id
     * @return customer with specified id
     */
    UserDTO getCustomerById(Long customerId);
    
    /**
     * Finds and returns list of customers with specified name
     * 
     * @param name Name of customer
     * @return list of customers with specified id
     */
    List<UserDTO> getCustomerByName(String name);
    
    /**
     * Finds and returns customer with specified email
     * 
     * @param email Email of customer
     * @return list of customers with specified email
     */
    UserDTO getCustomerByEmail(String email);
}
