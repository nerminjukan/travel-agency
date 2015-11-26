package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.entity.Customer;
import cz.muni.fi.pa165.travelagency.facade.CustomerFacade;
import cz.muni.fi.pa165.travelagency.service.BeanMappingService;
import cz.muni.fi.pa165.travelagency.service.CustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Radovan Sinko
 */
public class CustomerFacadeImpl implements CustomerFacade {
    
    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private CustomerService customerService;
    
    @Override
    public void createCustomer(CustomerDTO c) {
        customerService.createCustomer(beanMappingService.mapTo(c, Customer.class));
    }

    @Override
    public void updateCustomer(CustomerDTO c) {
        customerService.updateCustomer(beanMappingService.mapTo(c, Customer.class));
    }

    @Override
    public void removeCustomer(CustomerDTO c) {
        customerService.removeCustomer(beanMappingService.mapTo(c, Customer.class));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return beanMappingService.mapTo(customerService.findAll(), CustomerDTO.class);
    }

    @Override
    public CustomerDTO getCustomerById(Long customerId) {
        return beanMappingService.mapTo(customerService.findById(customerId), CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getCustomerByName(String name) {
        return beanMappingService.mapTo(customerService.findByName(name), CustomerDTO.class);
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        return beanMappingService.mapTo(customerService.findByEmail(email), CustomerDTO.class);
    }
}
