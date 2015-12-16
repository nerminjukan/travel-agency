package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.entity.User;
import java.util.List;

/**
 * Service layer interface for user entity
 * 
 * @author Radovan Sinko
 */
public interface UserService {

    /**
     * Authenticate user
     *
     * @param u user to authenticate
     * @param password password to check
     * @return true if password is correct, false otherwise
     */
    boolean authUser(User u, String password);

    /**
     * Determine if user is admin
     *
     * @param u user to check
     * @return true if specified user is admin, false otherwise
     */
    boolean isAdmin(long userId);

    /**
     * Create new user
     * 
     * @param u User to be created
     * @return created user
     */
    User registerUser(User u, String unencryptedPassword);

    /**
     * Update user
     * 
     * @param u User to be updated
     * @return updated user
     */
    User updateUser(User u);
    
    /**
     * Remove user
     * 
     * @param u User to be removed
     */
    void removeUser(User u);

    /**
     * Finds and returns all users
     * 
     * @return list of all users
     */
    List<User> findAll();
    
    /**
     * Finds and returns user with specified id
     * 
     * @param userId User id
     * @return user with specified id
     */
    User findById(Long userId);
    
    /**
     * Finds and returns list of users with specified name
     * 
     * @param name Name of user
     * @return list of users with specified id
     */
    List<User> findByName(String name);
    
    /**
     * Finds and returns user with specified email
     * 
     * @param email Email of user
     * @return list of users with specified email
     */
    User findByEmail(String email);
}
