package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dao.UserDao;
import cz.muni.fi.pa165.travelagency.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of UserService interface
 * 
 * @author Radovan Sinko
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao customerDao;
    
    @Override
    public User createCustomer(User c) {
        customerDao.create(c);
        return c;
    }

    @Override
    public User updateCustomer(User c) {
        return customerDao.update(c);
    }

    @Override
    public void removeCustomer(User c) {
        customerDao.remove(c);
    }

    @Override
    public List<User> findAll() {
        return customerDao.findAll();
    }

    @Override
    public User findById(Long customerId) {
        return customerDao.findById(customerId);
    }

    @Override
    public List<User> findByName(String name) {
        return customerDao.findByName(name);
    }

    @Override
    public User findByEmail(String email) {
        return customerDao.findByEmail(email);
    }

}
