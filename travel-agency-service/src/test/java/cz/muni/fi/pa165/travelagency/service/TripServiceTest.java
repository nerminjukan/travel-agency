package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dao.TripDao;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * @author Ondrej Glasnak
 * date 24/11/15
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TripServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private TripDao tripDao;

    private Trip trip1, trip2;

    @Autowired
    @InjectMocks
    private TripService tripService;

    @BeforeClass
    public void setUpClass() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setUpMethod() {
        trip1 = new Trip();
        trip1.setId((long) 11);
        trip1.setName("trip1");
        trip1.setDateFrom(Date.valueOf("2016-01-01"));
        trip1.setDateTo(Date.valueOf("2016-01-15"));
        trip1.setDestination("dest1");
        trip1.setPrice(new BigDecimal("1000"));
        trip1.setAvailableTrips((long) 10);

        trip2 = new Trip();
        trip1.setId((long) 12);
        trip2.setName("trip2");
        trip2.setDateFrom(Date.valueOf("2016-02-01"));
        trip2.setDateTo(Date.valueOf("2016-02-15"));
        trip2.setDestination("dest2");
        trip2.setPrice(new BigDecimal("2000"));
        trip2.setAvailableTrips((long) 20);
    }

    @Test
    public void testFindById() {
        trip1.setId((long) 420);
        when(tripDao.findById(trip1.getId())).thenReturn(trip1);
        Trip trip = tripService.findById(trip1.getId());
        assertDeepEquals(trip, trip1);
    }

    @Test
    public void testFindByIdNotExisting() {
        when(tripDao.findById(Long.MIN_VALUE)).thenReturn(null);
        assertNull(tripService.findById(Long.MIN_VALUE));
    }

    @Test
    public void testAddExcursionToTrip() {
        Excursion e = new Excursion((long) 0);
        int excursions = trip1.getExcursions().size();
        tripService.addExcursionToTrip(trip1, e);
        verify(tripDao).update(trip1);
        assertEquals(trip1.getExcursions().size(), excursions+1);
    }

    @Test
    public void testFindByName() {
        when(tripDao.findByName(trip1.getName())).thenReturn(trip1);
        Trip trip = tripService.findByName(trip1.getName());
        assertDeepEquals(trip, trip1);
    }

    @Test
    public void testFindAll() {
        when(tripDao.findAll()).thenReturn(new ArrayList<>());
        assertEquals(tripService.findAll().size(), 0);
        when(tripDao.findAll()).thenReturn(Collections.singletonList(trip1));
        assertEquals(tripService.findAll().size(), 1);
        when(tripDao.findAll()).thenReturn(Arrays.asList(trip1, trip2));
        assertEquals(tripService.findAll().size(), 2);
    }

    @Test
    public void testCreateTrip() {
        tripService.createTrip(trip1);
        verify(tripDao).create(trip1);
    }

    @Test
    public void testUpdateTrip() {
        trip1.setName("new Name");
        tripService.updateTrip(trip1);
        verify(tripDao).update(trip1);
    }

    @Test
    public void testDeleteTrip() {
        tripService.deleteTrip(trip1);
        verify(tripDao).remove(trip1);
    }

    private void assertDeepEquals(Trip t1, Trip t2) {
        assertEquals(t1,t2);
        assertEquals(t1.getId(),t2.getId());
        assertEquals(t1.getName(),t2.getName());
        assertEquals(t1.getDescription(),t2.getDescription());
        assertEquals(t1.getDestination(),t2.getDestination());
        assertEquals(t1.getDateFrom(),t2.getDateFrom());
        assertEquals(t1.getDateTo(),t2.getDateTo());
        assertEquals(t1.getPrice(),t2.getPrice());
        assertEquals(t1.getExcursions(),t2.getExcursions());
        assertEquals(t1.getAvailableTrips(),t2.getAvailableTrips());
    }
}