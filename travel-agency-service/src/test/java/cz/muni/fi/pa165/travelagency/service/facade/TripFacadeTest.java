package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.User;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.service.*;
import org.hibernate.service.spi.ServiceException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.runners.MockitoJUnitRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * @author Ondrej Glasnak
 * date 27/11/15
 */
@RunWith(MockitoJUnitRunner.class)
public class TripFacadeTest {

    @Mock
    private TripService tripService;
    @Mock
    private ReservationService resService;
    @Mock
    private UserService userService;

    // TODO del
    @Mock
    private ExcursionService excursionService;

    private Trip t1, t2;
    private TripDTO tDTO1, tDTO2;
    private List<Trip> tList;
    private List<TripDTO> dtoList;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private TripFacade tripFacade = new TripFacadeImpl();

    @BeforeClass
    public void setUpClass() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        t1 = createTrip(1L);
        t2 = createTrip(2L);
        tDTO1 = createTripDTO(1L);
        tDTO2 = createTripDTO(2L);
        tList = new ArrayList<>();
        tList.add(t1);
        tList.add(t2);
        dtoList = new ArrayList<>();
        dtoList.add(tDTO1);
        dtoList.add(tDTO2);
    }

    private TripDTO createTripDTO(long Id) {
        TripDTO tripDTO = new TripDTO();
        tripDTO.setId(Id);
        tripDTO.setName("Trip_" + Id);
        tripDTO.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 2 + (int) Id)));
        tripDTO.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 2 + (int) Id).plusDays(1)));
        tripDTO.setDestination("Dest_" + Id);
        tripDTO.setPrice(new BigDecimal("40" + Id + ".0"));
        tripDTO.setAvailableTrips(100+Id);
        return tripDTO;
    }

    private Trip createTrip(long Id) {
        Trip trip = new Trip();
        trip.setId(Id);
        trip.setName("Trip_" + Id);
        trip.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 2 + (int) Id)));
        trip.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 2 + (int) Id).plusDays(1)));
        trip.setDestination("Dest_" + Id);
        trip.setPrice(new BigDecimal("40" + Id + ".0"));
        trip.setAvailableTrips(100+Id);
        return trip;
    }

    @Test
    public void testCreateTrip() {
        when(beanMappingService.mapTo(tDTO1, Trip.class)).thenReturn(t1);
        tripFacade.createTrip(tDTO1);
        verify(tripService).createTrip(t1);
    }

    @Test
    public void testDeleteTrip() {
        when(beanMappingService.mapTo(tDTO1, Trip.class)).thenReturn(t1);
        tripFacade.deleteTrip(tDTO1.getId());
        verify(tripService).removeTrip(t1);
    }

    @Test
    public void testUpdateTrip() {
        when(beanMappingService.mapTo(tDTO1, Trip.class)).thenReturn(t1);
        tripFacade.updateTrip(tDTO1);
        verify(tripService).updateTrip(t1);
    }

    @Test
    public void testGetAllTrips() {
        when(tripService.findAll()).thenReturn(tList);
        when(beanMappingService.mapTo(Matchers.anyCollection(), Matchers.eq(TripDTO.class))).thenReturn(dtoList);
        assertEquals(tripFacade.getAllTrips().size(), 2);
    }

    @Test
    public void testGetTripById() {
        when(tripService.findById(1L)).thenReturn(t1);
        when(beanMappingService.mapTo(t1, TripDTO.class)).thenReturn(tDTO1);
        assertEquals(tripFacade.getTripById(1L), tDTO1);
    }

    @Test
    public void testGetTripsByUser() {
        User c = new User(11L);
        Reservation r1 = new Reservation(21L);
        Reservation r2 = new Reservation(22L);
        r1.setUser(c);
        r2.setUser(c);
        r1.setTrip(t1);
        r2.setTrip(t2);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        when(userService.findById(11L)).thenReturn(c);
        when(resService.findByUser(c)).thenReturn(reservations);
        when(beanMappingService.mapTo(r1.getTrip(), TripDTO.class)).thenReturn(tDTO1);
        when(beanMappingService.mapTo(r1.getTrip(), TripDTO.class)).thenReturn(tDTO2);
        assertEquals(tripFacade.getTripsByUser(11L).size(), 2);
    }
    
    @Test
    public void testGetTripByExcursion(){
        Excursion ex = new Excursion(11L);
        Trip t = createTrip(3L);
        t.addExcursion(ex);
        
        ExcursionDTO exDTO = new ExcursionDTO();
        ex.setId(11L);
        TripDTO tDTO = createTripDTO(3L);
        Set<ExcursionDTO> exsDTO = new HashSet<>();
        exsDTO.add(exDTO);
        tDTO.setExcursions(exsDTO);
        
        when(tripService.getTripByExcursion(11L)).thenReturn(t);
        when(beanMappingService.mapTo(t, TripDTO.class)).thenReturn(tDTO);
        assertEquals(tripFacade.getTripById(1L), tDTO);
    }
}