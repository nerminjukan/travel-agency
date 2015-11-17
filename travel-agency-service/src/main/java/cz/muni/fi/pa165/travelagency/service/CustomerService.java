package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.entity.Customer;


public interface CustomerService {
    /**
     * Finds customer by specified id.
     * @param id customer id
     * @return customer with specified id. Null if customer with specified
     * id doesn't exist.
     */
    public Customer findById(Long id);
}
