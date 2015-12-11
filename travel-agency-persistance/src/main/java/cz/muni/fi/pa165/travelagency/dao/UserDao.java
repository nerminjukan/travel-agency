package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.entity.User;
import java.util.List;

/**
 * UserDao provides operations for User entity.
 *
 * @author Radovan Sinko
 */
public interface UserDao {
    /**
     * Add new User into DB.
     * 
     * @param c User to be added into DB
     */
    void create(User c);
    
    /**
     * Update existing User.
     * 
     * @param c User to be updated in DB
     * @return updated User
     */
    User update(User c);
    
    /**
     * Delete User from DB.
     * 
     * @param c User to be removed from DB
     */
    void remove(User c);
    
    /**
     * Returns list of all existing Customers.
     * 
     * @return list of all existing Customers
     */
    List<User> findAll();
    
    /**
     * Returns User by id.
     * 
     * @param id id of User
     * @return User with provided id
     */
    User findById(Long id);
    
    /**
     * Returns Customers with provided name.
     * 
     * @param name name of Customers
     * @return list of Customers with provided name
     */
    List<User> findByName(String name);
    
    /**
     * Returns Customers with provided email.
     * 
     * @param email email of Customers
     * @return list of Customers with provided email
     */
    User findByEmail(String email);
}
