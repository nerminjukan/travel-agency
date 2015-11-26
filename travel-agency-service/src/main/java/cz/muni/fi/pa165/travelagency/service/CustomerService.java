package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.entity.Customer;
import java.util.List;

/**
 * Service layer interface for customer entity
 * 
 * @author Radovan Sinko
 */
public interface CustomerService {
    
    /**
     * Create new customer
     * 
     * @param c Customer to be created
     * @return created customer
     */
    Customer createCustomer(Customer c);

    /**
     * Update customer
     * 
     * @param c Customer to be updated
     * @return updated customer
     */
    Customer updateCustomer(Customer c);
    
    /**
     * Remove customer
     * 
     * @param c Customer to be removed
     */
    void removeCustomer(Customer c);

    /**
     * Finds and returns all customers
     * 
     * @return list of all customers
     */
    List<Customer> findAll();
    
    /**
     * Finds and returns customer with specified id
     * 
     * @param customerId Customer id
     * @return customer with specified id
     */
    Customer findById(Long customerId);
    
    /**
     * Finds and returns list of customers with specified name
     * 
     * @param name Name of customer
     * @return list of customers with specified id
     */
    List<Customer> findByName(String name);
    
    /**
     * Finds and returns customer with specified email
     * 
     * @param email Email of customer
     * @return list of customers with specified email
     */
    Customer findByEmail(String email);
}
