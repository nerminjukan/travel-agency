package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.travelagency.entity.Customer;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.testng.Assert.assertEquals;


/**
 * tests for the
 * @author Ondrej Glasnak
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
//@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})

public class ReservationDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ReservationDao reservationDao;
    private Reservation res1, res2;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private TripDao tripDao;

    private Customer c1, c2;
    private Trip t1, t2;

    @BeforeMethod
    public void setUp() {

        c1 = new Customer();
        c1.setEmail("test@mail.com");
        c1.setName("Customer Name");
        c1.setPhoneNumber("1234567890");
        c2 = new Customer();
        c2.setEmail("second@gmail.com");
        c2.setName("Another Customer");
        c2.setPhoneNumber("1135813210");
        customerDao.create(c1);
        customerDao.create(c2);

        t1 = new Trip();
        t1.setName("albania");
        t1.setDateFrom(new Date(new Long("1000000")));
        t1.setDateTo(new Date(new Long("2000000")));
        t1.setDestination("Albaca");
        t1.setPrice(new BigDecimal("2000"));
        t1.setAvailibleTrips(new Long(100));

        t2 = new Trip();
        t2.setName("belarus");
        t2.setDateFrom(new Date(new Long("2000000")));
        t2.setDateTo(new Date(new Long("3000000")));
        t2.setDestination("Bella");
        t2.setPrice(new BigDecimal("4000"));
        t2.setAvailibleTrips(new Long(50));
        tripDao.create(t1);
        tripDao.create(t2);

        res1 = new Reservation();
        res1.setCustomer(c1);
        res1.setTrip(t1);

        res2 = new Reservation();
        res2.setCustomer(c1);
        res2.setTrip(t1);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNullCustomer() {
        res1.setCustomer(null);
        reservationDao.creat(res1);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNullTrip() {
        res1.setTrip(null);
        reservationDao.creat(res1);
    }

    @Test
    public void testUpdate() {
        reservationDao.creat(res1);
        res1.setCustomer(c2);
        reservationDao.update(res1);
        assertEquals(reservationDao.findById(res1.getId()).getTrip(), t1);
        assertEquals(reservationDao.findById(res1.getId()).getCustomer(), c2);
    }

    @Test
    public void testRemove() {
        reservationDao.creat(res1);
        Assert.assertTrue(reservationDao.findAll().size() == 1);
        reservationDao.remove(res1);
        Assert.assertTrue(reservationDao.findAll().isEmpty());
    }

    @Test
    public void testFindAll() {
        reservationDao.creat(res1);
        reservationDao.creat(res2);
        List<Reservation> resList = reservationDao.findAll();
        Assert.assertTrue(resList.size() == 2);
    }

    @Test
    public void testFindById() {
        reservationDao.creat(res1);
        Reservation res = reservationDao.findById(res1.getId());
        Assert.assertEquals(res1.getId(), res.getId());
        Assert.assertEquals(c1, res.getCustomer());
        Assert.assertEquals(t1, res.getTrip());
    }

    @Test
    public void testFindByCustomer() {
        reservationDao.creat(res1);
        reservationDao.creat(res2);
        res2.setCustomer(c1);
        List<Reservation> resList = reservationDao.findByCustomer(c1);
        Assert.assertTrue(resList.size() == 2);
        res2.setCustomer(c2);
        resList = reservationDao.findByCustomer(c2);
        Assert.assertTrue(resList.size() == 1);
    }

    @Test
    public void testFindByTrip() {
        reservationDao.creat(res1);
        reservationDao.creat(res2);
        res2.setCustomer(c1);
        List<Reservation> resList = reservationDao.findByCustomer(c1);
        Assert.assertTrue(resList.size() == 2);
        res2.setCustomer(c2);
        resList = reservationDao.findByCustomer(c2);
        Assert.assertTrue(resList.size() == 1);
    }

    @Test
    public void findByTrip() {
        reservationDao.creat(res1);
        reservationDao.creat(res2);
        res1.setTrip(t1);
        res2.setTrip(t1);
        List<Reservation> resList = reservationDao.findByTrip(t1);
        Assert.assertTrue(resList.size() == 2);
        resList = reservationDao.findByTrip(t2);
        Assert.assertTrue(resList.size() == 0);
        res2.setTrip(t2);
        resList = reservationDao.findByTrip(t2);
        Assert.assertTrue(resList.size() == 1);
    }
}