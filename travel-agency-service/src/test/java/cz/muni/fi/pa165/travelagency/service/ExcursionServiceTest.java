package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dao.ExcursionDao;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfiguration;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
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
 * @author omular
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ExcursionServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private ExcursionDao excursionDao;

    @Autowired
    @InjectMocks
    private ExcursionService excursionService;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Excursion excursion;

    @BeforeMethod
    public void setUpMethod() throws Exception {
        excursion = new Excursion();
        excursion.setName("Testing excursion");
        excursion.setPrice(new BigDecimal("10.0"));
        excursion.setDestination("Best destintion");
        excursion.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        excursion.setDateTo(Date.valueOf(LocalDate.of(2015, 2, 20)));
    }

    @Test
    public void testFindByIdNotExisting() {
        assertNull(excursionService.findById(Long.MIN_VALUE));
    }

    @Test
    public void testFindById() {
        excursionDao.create(excursion);
        assertDeepEquals(excursion, excursionService.findById(excursion.getId()));
    }

    @Test
    public void testFindAll() {
        assertEquals(excursionService.findAll().size(), 0);
        excursionDao.create(excursion);
        assertEquals(excursionService.findAll().size(), 1);
        Excursion e = new Excursion();
        e.setName("test");
        e.setDestination("dest");
        e.setPrice(BigDecimal.ZERO);
        e.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        e.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1)));
        excursionDao.create(e);
        assertEquals(excursionService.findAll().size(), 2);
    }

    @Test
    public void testCreateExcursion() {
        Excursion e = excursionService.createExcursion(excursion);
        assertDeepEquals(excursionDao.findById(e.getId()), excursion);
    }

    @Test
    public void testUpdateExcusion() {
        excursionDao.create(excursion);
        excursion.setName("Another name");
        excursion.setPrice(BigDecimal.ZERO);
        excursionService.updateExcusion(excursion);
        assertDeepEquals(excursion, excursionDao.findById(excursion.getId()));
    }

    @Test
    public void testDeleteExcursion() {
        excursionDao.create(excursion);
        assertNotNull(excursionDao.findById(excursion.getId()));
        excursionService.deleteExcursion(excursion);
        assertNull(excursionDao.findById(excursion.getId()));
    }

    @Test
    public void testFindByName() {
        excursionDao.create(excursion);
        assertDeepEquals(excursionService.findByName(excursion.getName()), excursion);
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
