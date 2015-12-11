package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationTotalPriceDTO;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.exceptions.TravelAgencyServiceException;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.service.BeanMappingService;
import cz.muni.fi.pa165.travelagency.service.UserService;
import cz.muni.fi.pa165.travelagency.service.ReservationService;
import cz.muni.fi.pa165.travelagency.service.TripService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jan Duda
 */
@Transactional
@Service
public class ReservationFacadeImpl implements ReservationFacade {

    @Autowired
    private BeanMappingService beanMappingService;
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private UserService customerService;

    @Autowired
    private TripService tripService;
    
    @Override
    public void createReservation(ReservationDTO r) {
        reservationService.createReservation(beanMappingService.mapTo(r, Reservation.class));
    }

    @Override
    public void updateReservation(ReservationDTO r) {
        reservationService.updateReservation(beanMappingService.mapTo(r, Reservation.class));
    }

    @Override
    public void removeReservation(ReservationDTO r) {
        reservationService.removeReservation(beanMappingService.mapTo(r, Reservation.class));
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        return beanMappingService.mapTo(reservationService.findAll(), ReservationDTO.class);
    }

    @Override
    public ReservationDTO getReservationById(Long reservationId) {
        return beanMappingService.mapTo(reservationService.findById(reservationId), ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getReservationsByCustomer(Long customerId) {
        return beanMappingService.mapTo(reservationService.findByCustomer(customerService.findById(customerId)), ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getReservationsByTrip(Long tripId) {
        return beanMappingService.mapTo(reservationService.findByTrip(tripService.findById(tripId)), ReservationDTO.class);
    }

    @Override
    public ReservationTotalPriceDTO getTotalPriceOfReservation(Long reservationId) {
        Reservation r = reservationService.findById(reservationId);
        BigDecimal price = reservationService.getTotalPrice(r);
        ReservationTotalPriceDTO dto = new ReservationTotalPriceDTO();
        dto.setReservation(beanMappingService.mapTo(r, ReservationDTO.class));
        dto.setPrice(price);
        return dto;
    }
}
