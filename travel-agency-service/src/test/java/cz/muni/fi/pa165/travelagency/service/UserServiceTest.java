package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dao.UserDao;
import cz.muni.fi.pa165.travelagency.entity.User;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfiguration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 *
 * @author Radovan Sinko
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private UserDao userDao;

    @Autowired
    @InjectMocks
    private UserService userService;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private User user, user2;

    @BeforeMethod
    public void beforeMethod() {
        user = new User();
        user.setName("Pepa Jedlicka");
        user.setEmail("pepa@jedlicka.cz");
        user.setPhoneNumber("+420 700 600 500");

        user2 = new User();
        user2.setName("Franta Haluska");
        user2.setEmail("franta@haluska.cz");
        user2.setPhoneNumber("+420 100 200 300");
    }

    @Test
    public void createUserTest() {
        userService.registerUser(user, "pass");
        verify(userDao).create(user);
    }

    @Test
    public void updateUser() {
        user.setName("Janko Hrasko");
        userService.updateUser(user);
        verify(userDao).update(user);
    }

    @Test
    public void removeUser() {
        userService.removeUser(user);
        verify(userDao).remove(user);
    }

    @Test
    public void findAll() {
        when(userDao.findAll()).thenReturn(new ArrayList<>());
        assertEquals(userService.findAll().size(), 0);
        when(userDao.findAll()).thenReturn(Collections.singletonList(user));
        assertEquals(userService.findAll().size(), 1);
        when(userDao.findAll()).thenReturn(Arrays.asList(user, user2));
        assertEquals(userService.findAll().size(), 2);
    }

    @Test
    public void findById() {
        when(userDao.findById(Mockito.any())).thenReturn(user);
        User nUser = userService.findById(user.getId());
        assertDeepEquals(nUser, user);
    }

    @Test
    public void findByName() {
        when(userDao.findByName("Janko")).thenReturn(new ArrayList<>());
        assertEquals(userService.findByName("Janko").size(), 0);
        when(userDao.findByName("Pepa Jedlicka")).thenReturn(Collections.singletonList(user));
        assertEquals(userService.findByName("Pepa Jedlicka").size(), 1);
        user2.setName("Pepa Jedlicka");
        when(userDao.findByName("Pepa Jedlicka")).thenReturn(Arrays.asList(user, user2));
        assertEquals(userService.findByName("Pepa Jedlicka").size(), 2);
        
    }

    @Test
    public void findByEmail() {
        when(userDao.findByEmail(user.getName())).thenReturn(user);
        User nUser = userService.findByEmail(user.getName());
        assertDeepEquals(nUser, user);
    }

    private void assertDeepEquals(User user, User otherUser) {
        assertEquals(user, otherUser);
        assertEquals(user.getId(), otherUser.getId());
        assertEquals(user.getName(), otherUser.getName());
        assertEquals(user.getEmail(), otherUser.getEmail());
        assertEquals(user.getPhoneNumber(), otherUser.getPhoneNumber());
    }

}
