package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dao.ExcursionDao;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfiguration;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
 * @author omular
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ExcursionServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
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
        when(excursionDao.findById(Long.MIN_VALUE)).thenReturn(null);
        assertNull(excursionService.findById(Long.MIN_VALUE));
    }

    @Test
    public void testFindById() {
        excursion.setId(1l);
        when(excursionDao.findById(excursion.getId())).thenReturn(excursion);
        assertDeepEquals(excursion, excursionService.findById(excursion.getId()));
    }

    @Test
    public void testFindAll() {
        when(excursionDao.findAll()).thenReturn(new ArrayList<>());
        assertEquals(excursionService.findAll().size(), 0);
        when(excursionDao.findAll()).thenReturn(Collections.singletonList(excursion));
        assertEquals(excursionService.findAll().size(), 1);
        Excursion e = new Excursion();
        e.setName("test");
        e.setDestination("dest");
        e.setPrice(BigDecimal.ZERO);
        e.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        e.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1)));
        when(excursionDao.findAll()).thenReturn(Arrays.asList(excursion, e));
        assertEquals(excursionService.findAll().size(), 2);
    }

    @Test
    public void testCreateExcursion() {
        Excursion e = excursionService.createExcursion(excursion);
        verify(excursionDao).create(excursion);
    }

    @Test
    public void testUpdateExcursion() {
        excursion.setName("Another name");
        excursion.setPrice(BigDecimal.ZERO);
        excursionService.updateExcursion(excursion);
        verify(excursionDao).update(excursion);
    }

    @Test
    public void testDeleteExcursion() {
        excursionService.deleteExcursion(excursion);
        verify(excursionDao).remove(excursion);
    }

    @Test
    public void testFindByName() {
        when(excursionDao.findByName(excursion.getName())).thenReturn(excursion);
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
