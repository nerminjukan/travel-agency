package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dao.ReservationDao;
import cz.muni.fi.pa165.travelagency.entity.Customer;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import cz.muni.fi.pa165.travelagency.exceptions.TravelAgencyServiceException;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Jan Duda
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private TripService tripService;

    @Override
    public Reservation createReservation(Reservation r) {
        if (tripService.getNumberOfAvailableTripsLeft(r.getTrip()) == 0l) {
            throw new TravelAgencyServiceException("Cannot create new reservation for selected trip. There is no more free slot.");
        }
        reservationDao.create(r);
        return r;
    }

    @Override
    public Reservation updateReservation(Reservation r) {
        return reservationDao.update(r);
    }

    @Override
    public void removeReservation(Reservation r) {
        reservationDao.remove(r);
    }

    @Override
    public void addExcursionToReservation(Reservation r, Excursion e) {
        r.addExcursion(e);
        reservationDao.update(r);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }
    
    @Override
    public Reservation findById(Long id) {
        return reservationDao.findById(id);
    }

    @Override
    public List<Reservation> findByCustomer(Customer c) {
        return reservationDao.findByCustomer(c);
    }

    @Override
    public List<Reservation> findByTrip(Trip t) {
        return reservationDao.findByTrip(t);
    }

    @Override
    public BigDecimal getTotalPrice(Reservation r) {
        if (r == null) {
            throw new IllegalArgumentException();
        }
        BigDecimal totalPrice = r.getTrip().getPrice();
        for (Excursion e: r.getExcursions()) {
            totalPrice = totalPrice.add(e.getPrice());
        }
        return totalPrice;
    }
}
