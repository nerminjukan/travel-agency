package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.service.BeanMappingService;
import cz.muni.fi.pa165.travelagency.service.CustomerService;
import cz.muni.fi.pa165.travelagency.service.ExcursionService;
import cz.muni.fi.pa165.travelagency.service.ReservationService;
import cz.muni.fi.pa165.travelagency.service.TripService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jan Duda
 */
public class ReservationFacadeImpl implements ReservationFacade {

    @Autowired
    private BeanMappingService beanMappingService;
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private CustomerService customerService;
    
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
    public List<ReservationDTO> getAllReservation() {
        return beanMappingService.mapTo(reservationService.findAll(), ReservationDTO.class);
    }

    @Override
    public ReservationDTO getReservationById(Long reservationId) {
        return beanMappingService.mapTo(reservationService.findById(reservationId), ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getReservationByCustomer(Long customerId) {
        return beanMappingService.mapTo(reservationService.findByCustomer(customerService.findById(customerId)), ReservationDTO.class);
    }
    
}
