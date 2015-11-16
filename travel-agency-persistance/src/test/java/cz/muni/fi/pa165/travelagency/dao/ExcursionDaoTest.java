package cz.muni.fi.pa165.travelagency.dao;

import cz.muni.fi.pa165.travelagency.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Radovan Sinko
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ExcursionDaoTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private ExcursionDao excursionDao;
    
    private Excursion excursion1;
    private Excursion excursion2;
    private Excursion excursion3;
    
    @BeforeMethod
    public void setUp() {
        excursion1 = new Excursion();
        excursion1.setName("Name");
        excursion1.setDescription("Description");
        excursion1.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        excursion1.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1)));
        excursion1.setDestination("Germany");
        excursion1.setPrice(new BigDecimal("100.0"));
        
        excursion2 = new Excursion();
        excursion2.setName("Name1");
        excursion2.setDescription("Description1");
        excursion2.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        excursion2.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1)));
        excursion2.setDestination("Japan");
        excursion2.setPrice(new BigDecimal("200.0"));
        
        excursion3 = new Excursion();
        excursion3.setName("Name2");
        excursion3.setDescription("Description2");
        excursion3.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        excursion3.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1)));
        excursion3.setDestination("Japan");
        excursion3.setPrice(new BigDecimal("300.0"));
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testWithNullName() {
        excursion1.setName(null);
        excursionDao.create(excursion1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testWithNullDateFrom() {
        excursion1.setDateFrom(null);
        excursionDao.create(excursion1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testWithNullDateTo() {
        excursion1.setDateTo(null);
        excursionDao.create(excursion1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testWithNullDestination() {
        excursion1.setDestination(null);
        excursionDao.create(excursion1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testWithNullPrice() {
        excursion1.setPrice(null);
        excursionDao.create(excursion1);
    }
    
    @Test
    public void testUpdate() {
        excursionDao.create(excursion1);
        excursion1.setName("Name2");
        excursionDao.update(excursion1);
        assertEquals(excursionDao.findById(excursion1.getId()).getName(), "Name2");
        assertEquals(excursionDao.findById(excursion1.getId()).getDescription(), "Description");
        assertEquals(excursionDao.findById(excursion1.getId()).getDateFrom(), Date.valueOf(LocalDate.of(2015, 1, 1)));
        assertEquals(excursionDao.findById(excursion1.getId()).getDateTo(), Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1)));
        assertEquals(excursionDao.findById(excursion1.getId()).getDestination(), "Germany");
        assertEquals(excursionDao.findById(excursion1.getId()).getPrice(), new BigDecimal("100.0"));
    }

    @Test
    public void testRemove() {
        excursionDao.create(excursion1);
        assertTrue(excursionDao.findAll().contains(excursion1));
        assertTrue(excursionDao.findAll().size() == 1);
        excursionDao.remove(excursion1);
        assertTrue(excursionDao.findAll().isEmpty());
    }

    @Test
    public void testFindById() {
        excursionDao.create(excursion1);
        assertDeepEquals(excursionDao.findById(excursion1.getId()), excursion1);
    }

    @Test
    public void testFindByName() {
        excursionDao.create(excursion1);
        assertDeepEquals(excursionDao.findByName(excursion1.getName()), excursion1);
        excursionDao.create(excursion2);
        assertDeepEquals(excursionDao.findByName(excursion2.getName()), excursion2);
    }
    
    @Test
    public void testFindByDestination() {
        excursionDao.create(excursion1);
        assertDeepEquals(excursionDao.findByDestination(excursion1.getDestination()).get(0), excursion1);
        excursionDao.create(excursion2);
        assertDeepEquals(excursionDao.findByDestination(excursion2.getDestination()).get(0), excursion2);
        assertTrue(excursionDao.findByDestination(excursion2.getDestination()).size() == 1);
        excursionDao.create(excursion3);
        assertTrue(excursionDao.findByDestination(excursion2.getDestination()).size() == 2);
    }    
    
    @Test
    public void testFindAll() {
        excursionDao.create(excursion1);
        excursionDao.create(excursion2);
        assertTrue(excursionDao.findAll().contains(excursion1));
        assertTrue(excursionDao.findAll().contains(excursion2));
        assertTrue(excursionDao.findAll().size() == 2);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testInvalidDateRange() {
        LocalDate date = LocalDate.of(2015, 1, 1);
        excursion1.setDateFrom(Date.valueOf(date.plusDays(1)));
        excursion1.setDateTo(Date.valueOf(date));
        excursionDao.create(excursion1);
    }

    private void assertDeepEquals(Excursion excursion, Excursion excursion1) {
        assertEquals(excursion, excursion1);
        assertEquals(excursion.getId(), excursion1.getId());
        assertEquals(excursion.getName(), excursion1.getName());
        assertEquals(excursion.getDescription(), excursion1.getDescription());
        assertEquals(excursion.getDateFrom(), excursion1.getDateFrom());
        assertEquals(excursion.getDateTo(), excursion1.getDateTo());
        assertEquals(excursion.getDestination(), excursion1.getDestination());
        assertEquals(excursion.getPrice(), excursion1.getPrice());
    }
}
