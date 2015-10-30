package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.travelagency.entity.Customer;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Date;


/**
 * tests for the
 * @author Ondrej Glasnak
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ReservationDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ReservationDao reservationDao;
    private Reservation res1;
    private Reservation res2;

    private Customer c1;
    private Trip t1;

    @BeforeMethod
    public void setUp() {

        c1 = new Customer();
        c1.setEmail("test@mail.com");
        c1.setName("Customer Name");
        c1.setPhoneNumber("1234567890");

        t1 = new Trip();
        t1.setName("Trip 1");
        t1.setDateFrom(new Date(new Long("1000000")));
        t1.setDateTo(new Date(new Long("2000000")));
        t1.setDestination("Dest 1");
        t1.setPrice(new BigDecimal("2000"));
        t1.setAvailibleTrips(new Long(100));

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


}