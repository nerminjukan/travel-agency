package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.dto.TripCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.dto.TripUpdateDTO;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ondrej Glasnak
 *         date 22/11/15
 */
@Service
@Transactional
public class TripFacadeImpl implements TripFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private TripService tripService;
    @Autowired
    private ExcursionService excursionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;

    /**
     * Create new trip.
     *
     * @param t trip DTO to be created.
     */
    @Override
    public Long createTrip(TripCreateDTO t) {
        Trip createdTrip = tripService.createTrip(beanMappingService.mapTo(t, Trip.class));
        return createdTrip.getId();
    }

    /**
     * deletes trip with this Id
     * TODO: TBD: should this delete all associated excursions with trip?
     *
     * @param tripId if of trip to be deleted.
     */
    @Override
    public void deleteTrip(Long tripId) {
        
        List<Reservation> reservations = reservationService.findByTrip(tripService.findById(tripId));
        for (Reservation r : reservations) {
            reservationService.removeReservation(r);
        }
        Set<Excursion> excursions = tripService.findById(tripId).getExcursions();
        
        tripService.removeTrip(new Trip(tripId));
        for (Excursion e : excursions)
            excursionService.removeExcursion(e);
    }

    /**
     * updates trip with information specified in param t
     *
     * @param t trip DTO with updated information
     */
    @Override
    public void updateTrip(TripUpdateDTO t) {
        Trip newTrip = beanMappingService.mapTo(t, Trip.class);
        Trip oldTrip = tripService.findById(t.getId());
        for (Excursion e: oldTrip.getExcursions()) {
            newTrip.addExcursion(e);
        }
        tripService.updateTrip(newTrip);
    }

    /**
     * get list of all trips
     *
     * @return list of all trips
     */
    @Override
    public List<TripDTO> getAllTrips() {
        return beanMappingService.mapTo(tripService.findAll(), TripDTO.class);
    }

    /**
     * finds trip with specified Id and returns it
     *
     * @param tripId id of trip to be found
     * @return trip with this id or null if none found.
     */
    @Override
    public TripDTO getTripById(Long tripId) {
        return beanMappingService.mapTo(tripService.findById(tripId), TripDTO.class);
    }

    /**
     * finds all trips reserved by the specified user
     *
     * @param userId id of user
     * @return list of trips
     */
    @Override
    public List<TripDTO> getTripsByUser(Long userId) {
        List<Reservation> reservations = reservationService.findByUser(userService.findById(userId));
        // set for unique.
        Set<TripDTO> tripsSet = new HashSet<>();

        for (Reservation r : reservations) {
            tripsSet.add(beanMappingService.mapTo(r.getTrip(), TripDTO.class));
        }
        List<TripDTO> tripList = new ArrayList<>();
        tripList.addAll(tripsSet);
        return tripList;
    }
    
    @Override
    public TripDTO getTripByExcursion(Long excursionId){
        return beanMappingService.mapTo(tripService.getTripByExcursion(excursionId), TripDTO.class);
    }
}
