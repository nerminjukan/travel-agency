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
        t1.setAvailibleTrips(new Long(100));
        
        t2 = new Trip();
        t2.setName("Trip 2");
        t2.setDateFrom(new Date(new Long("6000000")));
        t2.setDateTo(new Date(new Long("10000000")));
        t2.setDestination("Dest 2");
        t2.setPrice(new BigDecimal("1000"));
        t2.setAvailibleTrips(new Long(50));
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
    public void testNullAvailibleTrips(){
        t1.setAvailibleTrips(null);
        tripDao.create(t1);
    }
    
    @Test
    public void testUpdate(){
        tripDao.create(t1);
        t1.setName("Trip with new name");
        tripDao.update(t1);
        Trip trip = tripDao.findById(t1.getId());
        Assert.assertEquals("Trip with new name", t1.getName());
        Assert.assertEquals(new Date(new Long("1000000")), t1.getDateFrom());
        Assert.assertEquals(new Date(new Long("2000000")), t1.getDateTo());
        Assert.assertEquals("Dest 1", t1.getDestination());
        Assert.assertEquals(new BigDecimal("2000"), t1.getPrice());
        Assert.assertEquals(new Long(100), t1.getAvailibleTrips());
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
        Assert.assertEquals(t1.getId(), t.getId());
        Assert.assertEquals(t1.getAvailibleTrips(), t.getAvailibleTrips());
        Assert.assertEquals(t1.getDateFrom(), t.getDateFrom());
        Assert.assertEquals(t1.getDateTo(), t.getDateTo());
        Assert.assertEquals(t1.getDestination(), t.getDestination());
        Assert.assertEquals(t1.getPrice(), t.getPrice());
    }
     
    @Test
    public void testIdIsNotInDB(){
        Trip t = tripDao.findById(Long.MIN_VALUE);
        Assert.assertNull(t);
    }
    
    @Test
    public void testFindByName(){
        tripDao.create(t1);
        tripDao.create(t2);
        List<Trip> trips = tripDao.findByName(t1.getName());
        Assert.assertTrue(trips.size() == 1);
        Trip t = trips.get(0);
        Assert.assertEquals(t1.getId(), t.getId());
        Assert.assertEquals(t1.getAvailibleTrips(), t.getAvailibleTrips());
        Assert.assertEquals(t1.getDateFrom(), t.getDateFrom());
        Assert.assertEquals(t1.getDateTo(), t.getDateTo());
        Assert.assertEquals(t1.getDestination(), t.getDestination());
        Assert.assertEquals(t1.getPrice(), t.getPrice());
    }
    
    @Test
    public void testNameIsNotInDB(){
        List<Trip> trips = tripDao.findByName("Trip123");
        Assert.assertTrue(trips.isEmpty());
    }
    
    @Test
    public void testFindByDestination(){
        tripDao.create(t1);
        tripDao.create(t2);
        List<Trip> trips = tripDao.findByDestination(t1.getDestination());
        Assert.assertTrue(trips.size() == 1);
        Trip t = trips.get(0);
        Assert.assertEquals(t1.getId(), t.getId());
        Assert.assertEquals(t1.getAvailibleTrips(), t.getAvailibleTrips());
        Assert.assertEquals(t1.getDateFrom(), t.getDateFrom());
        Assert.assertEquals(t1.getDateTo(), t.getDateTo());
        Assert.assertEquals(t1.getDestination(), t.getDestination());
        Assert.assertEquals(t1.getPrice(), t.getPrice());
    }
    
    @Test
    public void testDestinationIsNotInDB(){
        List<Trip> trips = tripDao.findByDestination("Destination1234");
        Assert.assertTrue(trips.isEmpty());
    }
}
