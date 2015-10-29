package cz.muni.fi.pa165.travel_agency.dao;

import cz.muni.fi.pa165.travel_agency.entity.Customer;
import java.util.List;

/**
 * CustomerDao provides operations for Customer entity.
 *
 * @author Radovan Sinko
 */
public interface CustomerDao {
    /**
     * Add new Customer into DB.
     * 
     * @param c Customer to be added into DB
     */
    void create(Customer c);
    
    /**
     * Update existing Customer.
     * 
     * @param c Customer to be updated in DB
     */
    void update(Customer c);
    
    /**
     * Delete Customer from DB.
     * 
     * @param c Customer to be removed from DB
     */
    void remove(Customer c);
    
    /**
     * Returns list of all existing Customers.
     * 
     * @return list of all existing Customers
     */
    List<Customer> findAll();
    
    /**
     * Returns Customer by id.
     * 
     * @param id id of Customer
     * @return Customer with provided id
     */
    Customer findById(Long id);
    
    /**
     * Returns Customers with provided name.
     * 
     * @param name name of Customers
     * @return list of Customers with provided name
     */
    List<Customer> findByName(String name);
    
    /**
     * Returns Customers with provided email.
     * 
     * @param email email of Customers
     * @return list of Customers with provided email
     */
    List<Customer> findByEmail(String email);
}
