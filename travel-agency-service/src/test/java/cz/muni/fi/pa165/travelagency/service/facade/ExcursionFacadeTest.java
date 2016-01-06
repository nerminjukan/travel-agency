/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.dto.ExcursionCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.dto.ExcursionUpdateDTO;
import cz.muni.fi.pa165.travelagency.entity.User;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.service.BeanMappingService;
import cz.muni.fi.pa165.travelagency.service.UserService;
import cz.muni.fi.pa165.travelagency.service.ExcursionService;
import cz.muni.fi.pa165.travelagency.service.ReservationService;
import cz.muni.fi.pa165.travelagency.service.TripService;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author omular
 */
@RunWith(MockitoJUnitRunner.class)
public class ExcursionFacadeTest {

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private ExcursionService excursionService;

    @Mock
    private UserService userService;

    @Mock
    private TripService tripService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ExcursionFacade excursionFacade = new ExcursionFacadeImpl();

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Excursion ex1;
    private Excursion ex2;

    private ExcursionDTO exDTO1;
    private ExcursionDTO exDTO2;

    private List<Excursion> exList;
    private List<ExcursionDTO> exDTOList;

    @BeforeMethod
    public void setUpMethod() throws Exception {
        ex1 = new Excursion(1l);
        ex1.setName("Name");
        ex1.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        ex1.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1)));
        ex1.setDestination("Germany");
        ex1.setPrice(new BigDecimal("100.0"));

        ex2 = new Excursion(2l);
        ex2.setName("Name1");
        ex2.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        ex2.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1)));
        ex2.setDestination("Japan");
        ex2.setPrice(new BigDecimal("200.0"));

        exDTO1 = new ExcursionDTO();
        exDTO1.setId(1l);
        exDTO1.setName("Name");
        exDTO1.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        exDTO1.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1)));
        exDTO1.setDestination("Germany");
        exDTO1.setPrice(new BigDecimal("100.0"));

        exDTO2 = new ExcursionDTO();
        exDTO2.setId(2l);
        exDTO2.setName("Name1");
        exDTO2.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        exDTO2.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1)));
        exDTO2.setDestination("Japan");
        exDTO2.setPrice(new BigDecimal("200.0"));

        exList = new ArrayList<>();
        exList.add(ex1);
        exList.add(ex2);

        exDTOList = new ArrayList<>();
        exDTOList.add(exDTO1);
        exDTOList.add(exDTO2);
    }

    @Test
    public void testGetAllExcursions() {
        when(excursionService.findAll()).thenReturn(exList);
        when(beanMappingService.mapTo(Matchers.anyCollection(), Matchers.eq(ExcursionDTO.class))).thenReturn(exDTOList);
        assertEquals(excursionFacade.getAllExcursions().size(), 2);
    }

    @Test
    public void testGetExcursionById() {
        when(excursionService.findById(1l)).thenReturn(ex1);
        when(beanMappingService.mapTo(ex1, ExcursionDTO.class)).thenReturn(exDTO1);
        assertEquals(excursionFacade.getExcursionById(1l), exDTO1);
    }

    @Test
    public void testGetExcursionsByUser() {
        User c = new User(3l);
        Reservation r = new Reservation(2l);
        r.setUser(c);
        r.addExcursion(ex1);
        r.addExcursion(ex2);
        when(userService.findById(3l)).thenReturn(c);
        when(reservationService.findByUser(c)).thenReturn(Collections.singletonList(r));
        when(beanMappingService.mapTo(r.getExcursions(), ExcursionDTO.class)).thenReturn(exDTOList);
        assertEquals(excursionFacade.getExcursionsByUser(3l).size(), 2);
    }

    @Test
    public void testDeleteExcursion() {
        excursionFacade.deleteExcursion(1l);
        verify(excursionService).removeExcursion(new Excursion(1l));
    }

    @Test
    public void testUpdateExcursion() {
        ExcursionUpdateDTO euDTO = new ExcursionUpdateDTO(exDTO1, 1l);
        Trip newTrip = new Trip(1l);
        Trip oldTrip = new Trip(2l);
        
        when(tripService.findById(2l)).thenReturn(oldTrip);
        when(tripService.findById(1l)).thenReturn(newTrip);
        when(beanMappingService.mapTo(euDTO, Excursion.class)).thenReturn(ex1);
        tripService.updateTrip(oldTrip);
        tripService.updateTrip(newTrip);
        verify(tripService).updateTrip(oldTrip);
        verify(tripService).updateTrip(newTrip);
        excursionFacade.updateExcursion(euDTO);
        verify(excursionService).updateExcursion(ex1);
    }

    @Test
    public void testCreateExcursion() {
        ExcursionCreateDTO ecDTO = new ExcursionCreateDTO();
        ecDTO.setName("test name");
        ecDTO.setDescription("desc");
        ecDTO.setPrice(BigDecimal.ZERO);
        ecDTO.setTripId(1l);
        Trip t = new Trip(1l);
        when(beanMappingService.mapTo(ecDTO, Excursion.class)).thenReturn(ex1);
        when(excursionService.createExcursion(ex1)).thenReturn(ex1);
        when(tripService.findById(1l)).thenReturn(t);
        excursionFacade.createExcursion(ecDTO);
        verify(tripService).addExcursionToTrip(t, ex1);
    }

}
