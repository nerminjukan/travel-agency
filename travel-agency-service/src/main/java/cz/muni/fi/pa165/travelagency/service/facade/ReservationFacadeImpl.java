package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.dto.ReservationCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationTotalPriceDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import cz.muni.fi.pa165.travelagency.entity.User;
import cz.muni.fi.pa165.travelagency.exceptions.TravelAgencyServiceException;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.service.BeanMappingService;
import cz.muni.fi.pa165.travelagency.service.ExcursionService;
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
    private UserService userService;

    @Autowired
    private TripService tripService;

    @Autowired
    private ExcursionService excursionService;
    
    @Override
    public Long createReservation(ReservationCreateDTO r, UserDTO user) {
        if (user == null) {
            throw new TravelAgencyServiceException("not existing user");
        }
        User u = userService.findById(user.getId());
        if (u == null) {
            throw new TravelAgencyServiceException("not existing user");
        }
        Trip trip = tripService.findById(r.getTripId());
        if (trip == null) {
            throw new TravelAgencyServiceException("not existing trip");
        }
        Reservation reservation = new Reservation();
        reservation.setTrip(trip);
        reservation.setUser(u);
        if (r.getExcursionsId() != null) {
            for (Long excursionId : r.getExcursionsId()) {
                Excursion ex = excursionService.findById(excursionId);
                if (ex != null) {
                    reservation.addExcursion(ex);
                }
            }
        }
        reservationService.createReservation(reservation);
        return reservation.getId();
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
        return updateTotalPrice(
                beanMappingService.mapTo(
                        reservationService.findAll(),
                        ReservationDTO.class
                )
        );
    }

    @Override
    public ReservationDTO getReservationById(Long reservationId) {
        Reservation r = reservationService.findById(reservationId);
        if (r == null) {
            return null;
        }
        return updateTotalPrice(
                beanMappingService.mapTo(
                        r, ReservationDTO.class
                )
        );
    }

    @Override
    public List<ReservationDTO> getReservationsByUser(Long userId) {
        return updateTotalPrice(
                beanMappingService.mapTo(
                        reservationService.findByUser(
                                userService.findById(userId)
                        ),
                        ReservationDTO.class
                )
        );
    }

    @Override
    public List<ReservationDTO> getReservationsByTrip(Long tripId) {
        return updateTotalPrice(
                beanMappingService.mapTo(
                        reservationService.findByTrip(
                                tripService.findById(tripId)
                        ),
                        ReservationDTO.class
                )
        );
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

    private ReservationDTO updateTotalPrice(ReservationDTO r) {
        r.setTotalPrice(
                reservationService.getTotalPrice(
                        beanMappingService.mapTo(r, Reservation.class)
                )
        );
        return r;
    }

    private List<ReservationDTO> updateTotalPrice(List<ReservationDTO> col) {
        for (ReservationDTO r : col) {
            updateTotalPrice(r);
        }
        return col;
    }
}
