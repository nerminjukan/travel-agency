package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dao.ReservationDao;
import cz.muni.fi.pa165.travelagency.entity.Customer;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
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

    @Override
    public Reservation createReservation(Reservation r) {
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
    
}
