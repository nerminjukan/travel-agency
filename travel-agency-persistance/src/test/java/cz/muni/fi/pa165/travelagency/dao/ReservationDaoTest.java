package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.travelagency.entity.User;
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
 * Tests for the ReservationDao
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
    private UserDao userDao;

    @Autowired
    private TripDao tripDao;

    private User c1, c2;
    private Trip t1, t2;

    @BeforeMethod
    public void setUp() {

        c1 = new User();
        c1.setEmail("test@mail.com");
        c1.setName("User Name");
        c1.setPhoneNumber("1234567890");
        c1.setPasswordHash("random_hash");
        c2 = new User();
        c2.setEmail("second@gmail.com");
        c2.setName("Another User");
        c2.setPhoneNumber("1135813210");
        c2.setPasswordHash("random_hash");
        userDao.create(c1);
        userDao.create(c2);

        t1 = new Trip();
        t1.setName("albania");
        t1.setDateFrom(new Date(new Long("1000000")));
        t1.setDateTo(new Date(new Long("2000000")));
        t1.setDestination("Albaca");
        t1.setPrice(new BigDecimal("2000"));
        t1.setAvailableTrips(new Long(100));

        t2 = new Trip();
        t2.setName("belarus");
        t2.setDateFrom(new Date(new Long("2000000")));
        t2.setDateTo(new Date(new Long("3000000")));
        t2.setDestination("Bella");
        t2.setPrice(new BigDecimal("4000"));
        t2.setAvailableTrips(new Long(50));
        tripDao.create(t1);
        tripDao.create(t2);

        res1 = new Reservation();
        res1.setUser(c1);
        res1.setTrip(t1);

        res2 = new Reservation();
        res2.setUser(c1);
        res2.setTrip(t1);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNullCustomer() {
        res1.setUser(null);
        reservationDao.create(res1);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNullTrip() {
        res1.setTrip(null);
        reservationDao.create(res1);
    }

    @Test
    public void testUpdate() {
        reservationDao.create(res1);
        res1.setUser(c2);
        reservationDao.update(res1);
        assertEquals(reservationDao.findById(res1.getId()).getTrip(), t1);
        assertEquals(reservationDao.findById(res1.getId()).getUser(), c2);
    }

    @Test
    public void testRemove() {
        reservationDao.create(res1);
        Assert.assertTrue(reservationDao.findAll().size() == 1);
        reservationDao.remove(res1);
        Assert.assertTrue(reservationDao.findAll().isEmpty());
    }

    @Test
    public void testFindAll() {
        reservationDao.create(res1);
        reservationDao.create(res2);
        List<Reservation> resList = reservationDao.findAll();
        Assert.assertTrue(resList.size() == 2);
    }

    @Test
    public void testFindById() {
        reservationDao.create(res1);
        Reservation res = reservationDao.findById(res1.getId());
        Assert.assertEquals(res1.getId(), res.getId());
        Assert.assertEquals(c1, res.getUser());
        Assert.assertEquals(t1, res.getTrip());
    }

    @Test
    public void testFindByCustomer() {
        reservationDao.create(res1);
        reservationDao.create(res2);
        res2.setUser(c1);
        List<Reservation> resList = reservationDao.findByUser(c1);
        Assert.assertTrue(resList.size() == 2);
        res2.setUser(c2);
        resList = reservationDao.findByUser(c2);
        Assert.assertTrue(resList.size() == 1);
    }

    @Test
    public void testFindByTrip() {
        reservationDao.create(res1);
        reservationDao.create(res2);
        res2.setUser(c1);
        List<Reservation> resList = reservationDao.findByUser(c1);
        Assert.assertTrue(resList.size() == 2);
        res2.setUser(c2);
        resList = reservationDao.findByUser(c2);
        Assert.assertTrue(resList.size() == 1);
    }

    @Test
    public void findByTrip() {
        reservationDao.create(res1);
        reservationDao.create(res2);
        res1.setTrip(t1);
        res2.setTrip(t1);
        List<Reservation> resList = reservationDao.findByTrip(t1);
        Assert.assertTrue(resList.size() == 2);
        resList = reservationDao.findByTrip(t2);
        Assert.assertTrue(resList.isEmpty());
        res2.setTrip(t2);
        resList = reservationDao.findByTrip(t2);
        Assert.assertTrue(resList.size() == 1);
    }
}