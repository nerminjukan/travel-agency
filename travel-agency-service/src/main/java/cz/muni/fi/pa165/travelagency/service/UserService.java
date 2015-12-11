package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.entity.User;
import java.util.List;

/**
 * Service layer interface for customer entity
 * 
 * @author Radovan Sinko
 */
public interface UserService {
    
    /**
     * Create new customer
     * 
     * @param c User to be created
     * @return created customer
     */
    User createCustomer(User c);

    /**
     * Update customer
     * 
     * @param c User to be updated
     * @return updated customer
     */
    User updateCustomer(User c);
    
    /**
     * Remove customer
     * 
     * @param c User to be removed
     */
    void removeCustomer(User c);

    /**
     * Finds and returns all customers
     * 
     * @return list of all customers
     */
    List<User> findAll();
    
    /**
     * Finds and returns customer with specified id
     * 
     * @param customerId User id
     * @return customer with specified id
     */
    User findById(Long customerId);
    
    /**
     * Finds and returns list of customers with specified name
     * 
     * @param name Name of customer
     * @return list of customers with specified id
     */
    List<User> findByName(String name);
    
    /**
     * Finds and returns customer with specified email
     * 
     * @param email Email of customer
     * @return list of customers with specified email
     */
    User findByEmail(String email);
}
