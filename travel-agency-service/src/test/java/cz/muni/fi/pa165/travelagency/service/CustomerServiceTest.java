package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dao.CustomerDao;
import cz.muni.fi.pa165.travelagency.entity.Customer;
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
public class CustomerServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private CustomerDao customerDao;

    @Autowired
    @InjectMocks
    private CustomerService customerService;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Customer customer, customer2;

    @BeforeMethod
    public void beforeMethod() {
        customer = new Customer();
        customer.setName("Pepa Jedlicka");
        customer.setEmail("pepa@jedlicka.cz");
        customer.setPhoneNumber("+420 700 600 500");

        customer2 = new Customer();
        customer2.setName("Franta Haluska");
        customer2.setEmail("franta@haluska.cz");
        customer2.setPhoneNumber("+420 100 200 300");
    }

    @Test
    public void createCustomerTest() {
        customerService.createCustomer(customer);
        verify(customerDao).create(customer);
    }

    @Test
    public void updateCustomer() {
        customer.setName("Janko Hrasko");
        customerService.updateCustomer(customer);
        verify(customerDao).update(customer);
    }

    @Test
    public void removeCustomer() {
        customerService.removeCustomer(customer);
        verify(customerDao).remove(customer);
    }

    @Test
    public void findAll() {
        when(customerDao.findAll()).thenReturn(new ArrayList<>());
        assertEquals(customerService.findAll().size(), 0);
        when(customerDao.findAll()).thenReturn(Collections.singletonList(customer));
        assertEquals(customerService.findAll().size(), 1);
        when(customerDao.findAll()).thenReturn(Arrays.asList(customer, customer2));
        assertEquals(customerService.findAll().size(), 2);
    }

    @Test
    public void findById() {
        when(customerDao.findById(Mockito.any())).thenReturn(customer);
        Customer nCustomer = customerService.findById(customer.getId());
        assertDeepEquals(nCustomer, customer);
    }

    @Test
    public void findByName() {
        when(customerDao.findByName("Janko")).thenReturn(new ArrayList<>());
        assertEquals(customerService.findByName("Janko").size(), 0);
        when(customerDao.findByName("Pepa Jedlicka")).thenReturn(Collections.singletonList(customer));
        assertEquals(customerService.findByName("Pepa Jedlicka").size(), 1);
        customer2.setName("Pepa Jedlicka");
        when(customerDao.findByName("Pepa Jedlicka")).thenReturn(Arrays.asList(customer, customer2));
        assertEquals(customerService.findByName("Pepa Jedlicka").size(), 2);
        
    }

    @Test
    public void findByEmail() {
        when(customerDao.findByEmail(customer.getName())).thenReturn(customer);
        Customer nCustomer = customerService.findByEmail(customer.getName());
        assertDeepEquals(nCustomer, customer);
    }

    private void assertDeepEquals(Customer customer, Customer otherCustomer) {
        assertEquals(customer, otherCustomer);
        assertEquals(customer.getId(), otherCustomer.getId());
        assertEquals(customer.getName(), otherCustomer.getName());
        assertEquals(customer.getEmail(), otherCustomer.getEmail());
        assertEquals(customer.getPhoneNumber(), otherCustomer.getPhoneNumber());
    }

}
