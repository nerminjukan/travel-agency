package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.dto.CustomerDTO;
import java.util.List;

/**
 * Facade layer for Customer entity
 * 
 * @author Radovan Sinko
 */
public interface CustomerFacade {

    /**
     * Create new customer
     * 
     * @param c DTO of customer to be created
     */
    void createCustomer(CustomerDTO c);

    /**
     * Update customer
     * 
     * @param c DTO of customer to be updated
     */
    void updateCustomer(CustomerDTO c);
    
    /**
     * Remove customer
     * 
     * @param c DTO of customer to be removed
     */
    void removeCustomer(CustomerDTO c);

    /**
     * Finds and returns all customers
     * 
     * @return list of all customers
     */
    List<CustomerDTO> getAllCustomers();
    
    /**
     * Finds and returns customer with specified id
     * 
     * @param customerId Customer id
     * @return customer with specified id
     */
    CustomerDTO getCustomerById(Long customerId);
    
    /**
     * Finds and returns list of customers with specified name
     * 
     * @param name Name of customer
     * @return list of customers with specified id
     */
    List<CustomerDTO> getCustomerByName(String name);
    
    /**
     * Finds and returns customer with specified email
     * 
     * @param email Email of customer
     * @return list of customers with specified email
     */
    CustomerDTO getCustomerByEmail(String email);
}
