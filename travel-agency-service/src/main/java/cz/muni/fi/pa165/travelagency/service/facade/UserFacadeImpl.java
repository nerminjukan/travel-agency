package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.dto.UserAuthenticateDTO;
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
    public void updateUser(UserDTO c) {
        customerService.updateUser(beanMappingService.mapTo(c, User.class));
    }

    @Override
    public void removeUser(UserDTO c) {
        customerService.removeUser(beanMappingService.mapTo(c, User.class));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return beanMappingService.mapTo(customerService.findAll(), UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Long customerId) {
        User u = customerService.findById(customerId);
        if (u == null) {
            return null;
        }
        return beanMappingService.mapTo(u, UserDTO.class);
    }

    @Override
    public List<UserDTO> getUserByName(String name) {
        return beanMappingService.mapTo(customerService.findByName(name), UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User u = customerService.findByEmail(email);
        if (u == null) {
            return null;
        }
        return beanMappingService.mapTo(u, UserDTO.class);
    }

    @Override
    public boolean isUserAdmin(UserDTO u) {
        return customerService.isAdmin(beanMappingService.mapTo(u, User.class));
    }

    @Override
    public UserDTO authUser(UserAuthenticateDTO u) {
        User user = customerService.findByEmail(u.getEmail());
        if (user == null) {
            return null;
        }
        if (customerService.authUser(user, u.getPassword())) {
            return beanMappingService.mapTo(user, UserDTO.class);
        }
        return null;
    }
}
