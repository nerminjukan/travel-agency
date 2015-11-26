package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Jan Duda
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TripDaoTest extends AbstractTestNGSpringContextTests{
    @Autowired
    private TripDao tripDao;
    
    private Trip t1;
    private Trip t2;
    
    @BeforeMethod
    public void setUp(){
        t1 = new Trip();
        t1.setName("Trip 1");
        t1.setDateFrom(new Date(new Long("1000000")));
        t1.setDateTo(new Date(new Long("2000000")));
        t1.setDestination("Dest 1");
        t1.setPrice(new BigDecimal("2000"));
        t1.setAvailableTrips(new Long(100));
        
        t2 = new Trip();
        t2.setName("Trip 2");
        t2.setDateFrom(new Date(new Long("6000000")));
        t2.setDateTo(new Date(new Long("10000000")));
        t2.setDestination("Dest 2");
        t2.setPrice(new BigDecimal("1000"));
        t2.setAvailableTrips(new Long(50));
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testNullName(){
        t1.setName(null);
        tripDao.create(t1);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testNullFrom(){
        t1.setDateFrom(null);
        tripDao.create(t1);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testNullTo(){
        t1.setDateTo(null);
        tripDao.create(t1);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testNullDestination(){
        t1.setDestination(null);
        tripDao.create(t1);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testNullPrice(){
        t1.setPrice(null);
        tripDao.create(t1);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testNullAvailableTrips(){
        t1.setAvailableTrips(null);
        tripDao.create(t1);
    }

    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testNegativeAvailableTrips(){
        t1.setAvailableTrips(new Long("-1"));
        tripDao.create(t1);
    }

    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testZeroAvailableTrips(){
        t1.setAvailableTrips(new Long("0"));
        tripDao.create(t1);
    }

    @Test
    public void testOneAvailableTrip() {
        t1.setAvailableTrips(new Long("1"));
        tripDao.create(t1);
        assertDeepEquals(tripDao.findById(t1.getId()), t1);
    }

    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testNegativePrice(){
        t1.setPrice(new BigDecimal("-1.0"));
        tripDao.create(t1);
    }

    @Test
    public void testZeroPrice(){
        t1.setPrice(new BigDecimal("0.0"));
        tripDao.create(t1);
        assertDeepEquals(tripDao.findById(t1.getId()), t1);
    }

    @Test
    public void testPositivePrice(){
        t1.setPrice(new BigDecimal("0.01"));
        tripDao.create(t1);
        assertDeepEquals(tripDao.findById(t1.getId()), t1);
    }

    @Test
    public void testUpdate(){
        tripDao.create(t1);
        t1.setName("Trip with new name");
        tripDao.update(t1);
        Trip trip = tripDao.findById(t1.getId());
        assertDeepEquals(t1, trip);
    }
    
    @Test
    public void testRemove(){
        tripDao.create(t1);
        Assert.assertTrue(tripDao.findAll().size() == 1);
        tripDao.remove(t1);
        Assert.assertTrue(tripDao.findAll().isEmpty());
    }
    
    @Test
    public void testFindAll(){
        tripDao.create(t1);
        tripDao.create(t2);
        Assert.assertTrue(tripDao.findAll().size() == 2);
    }
    
    @Test
    public void testFindById(){
        tripDao.create(t1);
        Trip t = tripDao.findById(t1.getId());
        assertDeepEquals(t1, t);
    }
     
    @Test
    public void testIdIsNotInDB(){
        Trip t = tripDao.findById(Long.MIN_VALUE);
        Assert.assertNull(t);
    }
    
    @Test
    public void testFindByNameSubstring() {
        Assert.assertTrue(tripDao.findByNameSubstring("Trip").isEmpty());
        tripDao.create(t1);
        tripDao.create(t2);
        Assert.assertTrue(tripDao.findByNameSubstring("Trip").size() == 2);
        List<Trip> trips = tripDao.findByNameSubstring("Trip 1");
        Assert.assertTrue(trips.size() == 1);
        Trip t = trips.get(0);
        assertDeepEquals(t, t1);
    }
    
    @Test
    public void testFindByName(){
        tripDao.create(t1);
        tripDao.create(t2);
        Trip t = tripDao.findByName(t1.getName());
        assertDeepEquals(t1, t);
    }
    
    @Test
    public void testNameIsNotInDB(){
        Assert.assertNull(tripDao.findByName("Trip123"));
    }

    @Test
    public void testFindByDestination(){
        tripDao.create(t1);
        tripDao.create(t2);
        List<Trip> trips = tripDao.findByDestination(t1.getDestination());
        Assert.assertTrue(trips.size() == 1);
        Trip t = trips.get(0);
        assertDeepEquals(t1, t);
    }
    
    @Test
    public void testDestinationIsNotInDB(){
        List<Trip> trips = tripDao.findByDestination("Destination1234");
        Assert.assertTrue(trips.isEmpty());
    }
    
    private void assertDeepEquals(Trip actual, Trip expected) {
        Assert.assertEquals(actual, expected);
        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getAvailableTrips(), expected.getAvailableTrips());
        Assert.assertEquals(actual.getDateFrom(), expected.getDateFrom());
        Assert.assertEquals(actual.getDateTo(), expected.getDateTo());
        Assert.assertEquals(actual.getDestination(), expected.getDestination());
        Assert.assertEquals(actual.getPrice(), expected.getPrice());
    }
}
