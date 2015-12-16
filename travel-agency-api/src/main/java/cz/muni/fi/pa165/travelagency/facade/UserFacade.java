package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import java.util.List;

/**
 * Facade layer for User entity 
 * 
 * @author Radovan Sinko
 */
public interface UserFacade {

    /**
     * Update user
     * 
     * @param u DTO of user to be updated
     */
    void updateUser(UserDTO u);
    
    /**
     * Remove user
     * 
     * @param u DTO of user to be removed
     */
    void removeUser(UserDTO u);

    /**
     * Finds and returns all users
     * 
     * @return list of all users
     */
    List<UserDTO> getAllUsers();
    
    /**
     * Finds and returns user with specified id
     * 
     * @param userId user id
     * @return user with specified id
     */
    UserDTO getUserById(Long userId);
    
    /**
     * Finds and returns list of users with specified name
     * 
     * @param name Name of user
     * @return list of users with specified id
     */
    List<UserDTO> getUserByName(String name);
    
    /**
     * Finds and returns user with specified email
     * 
     * @param email Email of user
     * @return list of users with specified email
     */
    UserDTO getUserByEmail(String email);

    /**
     * Check if user is admin
     *
     * @param u user
     * @return true if specified user is admin, false otherwise
     */
    boolean isUserAdmin(long userId);

    /**
     * Authenticate user
     *
     * @param u user credentials
     * @return authenticated user DTO if correct email and password entered, null otherwise
     */
    UserDTO authUser(UserAuthenticateDTO u);
}
