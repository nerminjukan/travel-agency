package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.entity.User;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.service.BeanMappingService;
import cz.muni.fi.pa165.travelagency.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Radovan Sinko
 */
@Transactional
@Service
public class UserFacadeImpl implements UserFacade {
    
    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private UserService customerService;
    
    @Override
    public void createCustomer(UserDTO c) {
        customerService.createCustomer(beanMappingService.mapTo(c, User.class));
    }

    @Override
    public void updateCustomer(UserDTO c) {
        customerService.updateCustomer(beanMappingService.mapTo(c, User.class));
    }

    @Override
    public void removeCustomer(UserDTO c) {
        customerService.removeCustomer(beanMappingService.mapTo(c, User.class));
    }

    @Override
    public List<UserDTO> getAllCustomers() {
        return beanMappingService.mapTo(customerService.findAll(), UserDTO.class);
    }

    @Override
    public UserDTO getCustomerById(Long customerId) {
        return beanMappingService.mapTo(customerService.findById(customerId), UserDTO.class);
    }

    @Override
    public List<UserDTO> getCustomerByName(String name) {
        return beanMappingService.mapTo(customerService.findByName(name), UserDTO.class);
    }

    @Override
    public UserDTO getCustomerByEmail(String email) {
        return beanMappingService.mapTo(customerService.findByEmail(email), UserDTO.class);
    }
}
