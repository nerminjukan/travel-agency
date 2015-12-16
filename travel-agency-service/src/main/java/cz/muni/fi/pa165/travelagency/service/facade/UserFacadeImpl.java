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
    private UserService userService;

    @Override
    public void updateUser(UserDTO u) {
        userService.updateUser(beanMappingService.mapTo(u, User.class));
    }

    @Override
    public void removeUser(UserDTO u) {
        userService.removeUser(beanMappingService.mapTo(u, User.class));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return beanMappingService.mapTo(userService.findAll(), UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User u = userService.findById(userId);
        if (u == null) {
            return null;
        }
        return beanMappingService.mapTo(u, UserDTO.class);
    }

    @Override
    public List<UserDTO> getUserByName(String name) {
        return beanMappingService.mapTo(userService.findByName(name), UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User u = userService.findByEmail(email);
        if (u == null) {
            return null;
        }
        return beanMappingService.mapTo(u, UserDTO.class);
    }

    @Override
    public boolean isUserAdmin(long userId) {
        return userService.isAdmin(userId);
    }

    @Override
    public UserDTO authUser(UserAuthenticateDTO u) {
        User user = userService.findByEmail(u.getEmail());
        if (user == null) {
            return null;
        }
        if (userService.authUser(user, u.getPassword())) {
            return beanMappingService.mapTo(user, UserDTO.class);
        }
        return null;
    }
}
