package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.entity.User;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.service.BeanMappingService;
import cz.muni.fi.pa165.travelagency.service.UserService;
import cz.muni.fi.pa165.travelagency.service.ExcursionService;
import cz.muni.fi.pa165.travelagency.service.ReservationService;
import cz.muni.fi.pa165.travelagency.service.TripService;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Radovan Sinko
 */
@RunWith(MockitoJUnitRunner.class)
public class UserFacadeTest {

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private ExcursionService excursionService;

    @Mock
    private UserService userService;

    @Mock
    private TripService tripService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private UserFacade userFacade = new UserFacadeImpl();

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private User user1;
    private User user2;

    private UserDTO userDTO1;
    private UserDTO userDTO2;

    private List<User> userList;
    private List<UserDTO> userDTOList;

    @BeforeMethod
    public void setUpMethod() throws Exception {
        user1 = new User(1l);
        user1.setName("Admin");
        user1.setPhoneNumber("0123456789");
        user1.setEmail("admin@email.com");
        user1.setIsAdmin(true);
        user1.setPasswordHash("Password");

        user2 = new User(2l);
        user2.setName("User");
        user2.setPhoneNumber("9876543210");
        user2.setEmail("email@email.com");
        user2.setIsAdmin(false);
        user2.setPasswordHash("Password");

        userDTO1 = new UserDTO();
        userDTO1.setId(1l);
        userDTO1.setName("Admin");
        userDTO1.setPhoneNumber("0123456789");
        userDTO1.setIsAdmin(true);

        userDTO2 = new UserDTO();
        userDTO2.setId(2l);
        userDTO2.setName("User");
        userDTO2.setPhoneNumber("9876543210");
        userDTO2.setIsAdmin(false);

        userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        userDTOList = new ArrayList<>();
        userDTOList.add(userDTO1);
        userDTOList.add(userDTO2);
    }

    @Test
    public void testGetAllUsers() {
        when(userService.findAll()).thenReturn(userList);
        when(beanMappingService.mapTo(Matchers.anyCollection(), Matchers.eq(UserDTO.class))).thenReturn(userDTOList);
        assertEquals(userFacade.getAllUsers().size(), 2);
    }

    @Test
    public void testGetUserById() {
        when(userService.findById(1l)).thenReturn(user1);
        when(beanMappingService.mapTo(user1, UserDTO.class)).thenReturn(userDTO1);
        assertEquals(userFacade.getUserById(1l), userDTO1);
    }

    @Test
    public void testGetUserByEmail() {
        when(userService.findByEmail("admin@email.com")).thenReturn(user1);
        when(beanMappingService.mapTo(user1, UserDTO.class)).thenReturn(userDTO1);
        assertEquals(userFacade.getUserByEmail("admin@email.com"), userDTO1);
    }

    @Test
    public void testGetUserByName() {
        when(userService.findByName("Admin")).thenReturn(userList);
        when(beanMappingService.mapTo(Matchers.anyCollection(), Matchers.eq(UserDTO.class))).thenReturn(userDTOList);
        assertEquals(userFacade.getUserByName("Admin"), userDTO1);
    }

    @Test
    public void testRemoveUser() {
        when(beanMappingService.mapTo(userDTO1, User.class)).thenReturn(user1);
        userFacade.removeUser(userDTO1);
        verify(userService).removeUser(user1);
    }

    @Test
    public void testUpdateUser() {
        when(beanMappingService.mapTo(userDTO1, User.class)).thenReturn(user1);
        userFacade.updateUser(userDTO1);
        verify(userService).updateUser(user1);
    }

    @Test
    public void testIsUserAdmin() {
        assertEquals(userFacade.isUserAdmin(user1.getId()), true);
        assertEquals(userFacade.isUserAdmin(user2.getId()), false);
    }
    
}
