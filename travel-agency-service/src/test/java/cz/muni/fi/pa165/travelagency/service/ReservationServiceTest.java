package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dao.ReservationDao;
import cz.muni.fi.pa165.travelagency.entity.Customer;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfiguration;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Jan Duda
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ReservationServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    
    @Mock
    private ReservationDao reservationDao;
    
    @Autowired
    @InjectMocks
    private ReservationService reservationService;
    
    @BeforeClass
    public void setUp() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    private Reservation testReservation;
    
    @BeforeMethod
    public void prepareTestReservation(){
        
        Customer customer = createCustomer(0);
        Trip trip = createTrip(0);
        Excursion excursion = createExcursion(0);
        
        testReservation = new Reservation();
        testReservation.setTrip(trip);
        testReservation.setCustomer(customer);
        testReservation.addExcursion(excursion);
    }
    
    @Test
    public void testCreateReservation(){
        reservationService.createReservation(testReservation);
        verify(reservationDao).create(testReservation);
    }
    
    @Test
    public void testUpdateReservation(){
        reservationService.updateReservation(testReservation);
        verify(reservationDao).update(testReservation);
    }
    
    @Test
    public void testRemoveReservation(){
        reservationService.removeReservation(testReservation);
        verify(reservationDao).remove(testReservation);
    }
    
    @Test
    public void testFindAll(){
        when(reservationDao.findAll()).thenReturn(new ArrayList<>());
        assertEquals(reservationService.findAll().size(), 0);
        when(reservationDao.findAll()).thenReturn(Collections.singletonList(testReservation));
        assertEquals(reservationService.findAll().size(), 1);
        
        Reservation r = new Reservation();
        r.setCustomer(createCustomer(1));
        r.setTrip(createTrip(1));
        r.addExcursion(createExcursion(1));
        
        when(reservationDao.findAll()).thenReturn(Arrays.asList(testReservation, r));
        assertEquals(reservationService.findAll().size(), 2);
    }
    
    @Test
    public void testFindById(){
        testReservation.setId(1l);
        when(reservationDao.findById(testReservation.getId())).thenReturn(testReservation);
        assertDeepEquals(reservationService.findById(testReservation.getId()), testReservation);
    }
    
    @Test
    public void testFindByIdWhichDoesntExist(){
        when(reservationDao.findById(Long.MIN_VALUE)).thenReturn(null);
        assertNull(reservationService.findById(Long.MIN_VALUE));
    }
    
    @Test
    public void testFindByCustomer(){
        Customer c = testReservation.getCustomer();
        when(reservationDao.findByCustomer(c)).thenReturn(Collections.singletonList(testReservation));
        List<Reservation> l = reservationService.findByCustomer(c);
        assertEquals(l.size(), 1);
        assertDeepEquals(l.get(0), testReservation);
    }
    
    @Test
    public void testFindByCustomerWhoDoesntExist(){
        Customer c = createCustomer(2);
        when(reservationDao.findByCustomer(c)).thenReturn(new ArrayList<>());
        assertEquals(reservationService.findByCustomer(c).size(), 0);
    }
    
    @Test
    public void testFindByTrip(){
        Trip t = testReservation.getTrip();
        when(reservationDao.findByTrip(t)).thenReturn(Collections.singletonList(testReservation));
        List<Reservation> l = reservationService.findByTrip(t);
        assertEquals(l.size(), 1);
        assertDeepEquals(l.get(0), testReservation);
    }
    
    @Test
    public void testFindByTripWhichDoesntExist(){
        Trip t = createTrip(2);
        when(reservationDao.findByTrip(t)).thenReturn(new ArrayList<>());
        assertEquals(reservationService.findByTrip(t).size(), 0);
    }
    
    private void assertDeepEquals(Reservation r1, Reservation r2){
        assertEquals(r1, r2);
        assertEquals(r1.getId(), r2.getId());
        assertTrue(r1.getCustomer().equals(r2.getCustomer()));
        assertTrue(r1.getTrip().equals(r2.getTrip()));
        assertTrue(r1.getExcursions().equals(r2.getExcursions()));
    }
    
    private Trip createTrip(int numberTrip){
        Trip trip = new Trip();
        trip.setName("Trip " + numberTrip);
        trip.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 2 + numberTrip)));
        trip.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 2 + numberTrip).plusDays(1)));
        trip.setDestination("Dest " + numberTrip);
        trip.setPrice(new BigDecimal("200" + numberTrip + ".0"));
        trip.setAvailableTrips(new Long(100));
        
        return trip;
    }
    
    private Customer createCustomer(int numberCustomer){
        Customer customer = new Customer();
        customer.setEmail("test" + numberCustomer + "@mail.com");
        customer.setName("Customer Name " + numberCustomer);
        customer.setPhoneNumber("123456789" + numberCustomer);
        return customer;
    }
    
    private Excursion createExcursion(int numberExcursion){
        Excursion excursion = new Excursion();
        excursion.setName("Excursion " + numberExcursion);
        excursion.setDescription("Description " + numberExcursion);
        excursion.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1 + numberExcursion)));
        excursion.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1 + numberExcursion).plusDays(1)));
        excursion.setDestination("Germany " + numberExcursion);
        excursion.setPrice(new BigDecimal("100" + numberExcursion + ".0"));
        return excursion;
    }
}
