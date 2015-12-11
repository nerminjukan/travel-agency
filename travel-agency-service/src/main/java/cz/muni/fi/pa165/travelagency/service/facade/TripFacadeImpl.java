package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
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
@Transactional
@Service
public class TripFacadeImpl implements TripFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private TripService tripService;
    @Autowired
    private ExcursionService excursionService;
    @Autowired
    private UserService customerService;
    @Autowired
    private ReservationService reservationService;

    /**
     * Create new trip.
     *
     * @param t trip DTO to be created.
     */
    @Override
    public void createTrip(TripDTO t) {
        tripService.createTrip(beanMappingService.mapTo(t, Trip.class));
    }

    /**
     * deletes trip with this Id
     * TODO: TBD: should this delete all associated excursions with trip?
     *
     * @param t trip to be deleted.
     */
    @Override
    public void deleteTrip(TripDTO t) {
        Set<ExcursionDTO> excursions = t.getExcursions();
        for (ExcursionDTO e : excursions)
            excursionService.removeExcursion(
                    beanMappingService.mapTo(e, Excursion.class));

        tripService.removeTrip(beanMappingService.mapTo(t, Trip.class));
    }

    /**
     * updates trip with information specified in param t
     *
     * @param t trip DTO with updated information
     */
    @Override
    public void updateTrip(TripDTO t) {
        tripService.updateTrip(beanMappingService.mapTo(t, Trip.class));
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
     * finds all trips reserved by the specified customer
     *
     * @param customerId id of customer
     * @return list of trips
     */
    @Override
    public List<TripDTO> getTripsByCustomer(Long customerId) {
        List<Reservation> reservations = reservationService.findByCustomer(
                customerService.findById(customerId));
        // set for unique.
        Set<TripDTO> tripsSet = new HashSet<>();

        for (Reservation r : reservations) {
            tripsSet.add(beanMappingService.mapTo(r.getTrip(), TripDTO.class));
        }
        List<TripDTO> tripList = new ArrayList<>();
        tripList.addAll(tripsSet);
        return tripList;
    }
}
