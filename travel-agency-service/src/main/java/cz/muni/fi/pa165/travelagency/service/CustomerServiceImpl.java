package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dao.CustomerDao;
import cz.muni.fi.pa165.travelagency.entity.Customer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of CustomerService interface
 * 
 * @author Radovan Sinko
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;
    
    @Override
    public Customer createCustomer(Customer c) {
        customerDao.create(c);
        return c;
    }

    @Override
    public Customer updateCustomer(Customer c) {
        return customerDao.update(c);
    }

    @Override
    public void removeCustomer(Customer c) {
        customerDao.remove(c);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public Customer findById(Long customerId) {
        return customerDao.findById(customerId);
    }

    @Override
    public List<Customer> findByName(String name) {
        return customerDao.findByName(name);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerDao.findByEmail(email);
    }

}
