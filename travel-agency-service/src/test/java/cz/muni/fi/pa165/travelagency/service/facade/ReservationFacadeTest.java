/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationTotalPriceDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.entity.Customer;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.service.BeanMappingService;
import cz.muni.fi.pa165.travelagency.service.CustomerService;
import cz.muni.fi.pa165.travelagency.service.ExcursionService;
import cz.muni.fi.pa165.travelagency.service.ReservationService;
import cz.muni.fi.pa165.travelagency.service.TripService;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.service.spi.ServiceException;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Jan Duda
 */
@RunWith(MockitoJUnitRunner.class)
public class ReservationFacadeTest {
    
    @Mock
    private BeanMappingService beanMappingService;
    
    @Mock
    private ExcursionService excursionService;
    
    @Mock
    private TripService tripService;
    
    @Mock
    private ReservationService reservationService;
    
    @Mock
    private CustomerService customerService;
    
    @InjectMocks
    private ReservationFacade reservationFacade = new ReservationFacadeImpl();
    
    private Reservation r1;
    private Reservation r2;
    
    private ReservationDTO rdto1;
    private ReservationDTO rdto2;
    
    private List<Reservation> rList;
    private List<Reservation> allReservations;
    private List<ReservationDTO> rdtoList;
    private List<ReservationDTO> allReservationsDto;
    
    @BeforeClass
    public void setup() throws ServiceException{
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeMethod
    public void setUpMethod() {
        r1 = createReservation(1L);
        r2 = createReservation(2L);
        
        rdto1 = createReservationDTO(1L);
        rdto2 = createReservationDTO(2L);
        
        rList = new ArrayList<>();
        rList.add(r1);
        
        allReservations = new ArrayList<>();
        allReservations.add(r1);
        allReservations.add(r2);
        
        rdtoList = new ArrayList<>();
        rdtoList.add(rdto1);
        
        allReservationsDto = new ArrayList<>();
        allReservationsDto.add(rdto1);
        allReservationsDto.add(rdto2);
    }
    
    @Test
    public void testCreateReservation(){
        when(beanMappingService.mapTo(rdto1, Reservation.class)).thenReturn(r1);
        reservationFacade.createReservation(rdto1);
        verify(reservationService).createReservation(r1);
    }
    
    @Test
    public void testUpdateReservation(){
        when(beanMappingService.mapTo(rdto1, Reservation.class)).thenReturn(r1);
        reservationFacade.updateReservation(rdto1);
        verify(reservationService).updateReservation(r1);
    }
    
    @Test
    public void testRemoveReservation(){
        when(beanMappingService.mapTo(rdto1, Reservation.class)).thenReturn(r1);
        reservationFacade.removeReservation(rdto1);
        verify(reservationService).removeReservation(r1);
    }
    
    @Test
    public void testGetAllReservations(){
        when(reservationService.findAll()).thenReturn(allReservations);
        when(beanMappingService.mapTo(Matchers.anyCollection(), Matchers.eq(ReservationDTO.class))).thenReturn(allReservationsDto);
        assertEquals(reservationFacade.getAllReservations().size(), 2);
    }
    
    @Test
    public void testGetReservationById(){
        when(reservationService.findById(1L)).thenReturn(r1);
        when(beanMappingService.mapTo(r1, ReservationDTO.class)).thenReturn(rdto1);
        assertEquals(reservationFacade.getReservationById(1L), rdto1);
    }
    
    @Test
    public void testGetReservationsByCustomer(){
        Customer c = new Customer(1L);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
       
        when(customerService.findById(1L)).thenReturn(c);
        when(reservationService.findByCustomer(c)).thenReturn(reservations);
        
        when(beanMappingService.mapTo(reservations, ReservationDTO.class)).thenReturn(rdtoList);
        assertEquals(reservationFacade.getReservationsByCustomer(1L).size(), 1);
    }
    
    @Test
    public void testGetReservationsByTrip(){
        Trip t = new Trip(1L);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
       
        when(tripService.findById(1L)).thenReturn(t);
        when(reservationService.findByTrip(t)).thenReturn(reservations);
        
        when(beanMappingService.mapTo(reservations, ReservationDTO.class)).thenReturn(rdtoList);
        assertEquals(reservationFacade.getReservationsByCustomer(1L).size(), 1);
    }
    
    @Test
    public void testGetTotalPriceOfReservation(){
        BigDecimal price = new BigDecimal("302.0");
        
        when(reservationService.findById(1L)).thenReturn(r1);
        when(reservationService.getTotalPrice(r1)).thenReturn(price);
        when(beanMappingService.mapTo(r1, ReservationDTO.class)).thenReturn(rdto1);
        
        assertEquals(reservationFacade.getTotalPriceOfReservation(1L).getPrice(), new BigDecimal("302.0"));
    }
    
    
    private Reservation createReservation(long id){
        Reservation r = new Reservation(id);
        r.setCustomer(createCustomer(id));
        r.setTrip(createTrip(id));
        r.addExcursion(createExcursion(id));
        
        return r;
    }
    
    private Trip createTrip(long id){
        Trip trip = new Trip();
        trip.setName("Trip " + id);
        trip.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 2 + (int) id)));
        trip.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 2 + (int) id).plusDays(1)));
        trip.setDestination("Dest " + id);
        trip.setPrice(new BigDecimal("200" + id + ".0"));
        trip.setAvailableTrips(new Long(100));
        
        return trip;
    }
    
    private Customer createCustomer(long id){
        Customer customer = new Customer();
        customer.setEmail("test" + id + "@mail.com");
        customer.setName("Customer Name " + id);
        customer.setPhoneNumber("123456789" + id);
        
        return customer;
    }
    
    private Excursion createExcursion(long id){
        Excursion excursion = new Excursion();
        excursion.setName("Excursion " + id);
        excursion.setDescription("Description " + id);
        excursion.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1 + (int) id)));
        excursion.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1 + (int) id).plusDays(1)));
        excursion.setDestination("Germany " + id);
        excursion.setPrice(new BigDecimal("100" + id + ".0"));
        
        return excursion;
    }
    
    private ReservationDTO createReservationDTO(long id){
        ReservationDTO rdto = new ReservationDTO();
        rdto.setId(id);
        rdto.setCustomer(createCustomerDTO(id));
        rdto.setTrip(createTripDTO(id));
        Set<ExcursionDTO> listOfExcursions = new HashSet<>();
        listOfExcursions.add(createExcursionDTO(id));
        rdto.setExcursions(listOfExcursions);
        
        return rdto;
    }
    
    private TripDTO createTripDTO(long id){
        TripDTO tripDTO = new TripDTO();
        tripDTO.setId(id);
        tripDTO.setName("Trip " + id);
        tripDTO.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 2 + (int) id)));
        tripDTO.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 2 + (int) id).plusDays(1)));
        tripDTO.setDestination("Dest " + id);
        tripDTO.setPrice(new BigDecimal("200" + id + ".0"));
        tripDTO.setAvailableTrips(new Long(100));
        
        return tripDTO;
    }
    
    private CustomerDTO createCustomerDTO(long id){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(id);
        customerDTO.setEmail("test" + id + "@mail.com");
        customerDTO.setName("Customer Name " + id);
        customerDTO.setPhoneNumber("123456789" + id);
        
        return customerDTO;
    }
    
    private ExcursionDTO createExcursionDTO(long id){
        ExcursionDTO excursionDTO = new ExcursionDTO();
        excursionDTO.setId(id);
        excursionDTO.setName("Excursion " + id);
        excursionDTO.setDescription("Description " + id);
        excursionDTO.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1 + (int) id)));
        excursionDTO.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1 + (int) id).plusDays(1)));
        excursionDTO.setDestination("Germany " + id);
        excursionDTO.setPrice(new BigDecimal("100" + id + ".0"));
        
        return excursionDTO;
    }
}
