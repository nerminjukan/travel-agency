package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dao.ExcursionDao;
import cz.muni.fi.pa165.travelagency.dao.TripDao;
import cz.muni.fi.pa165.travelagency.dao.ReservationDao;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import java.sql.Date;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ondrej Glasnak
 * date 22/11/15
 */
@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripDao tripDao;

    @Autowired
    private ReservationDao reservationDao;
    
    @Autowired
    private ExcursionDao excursionDao;

    @Override
    public Trip findById(Long id) {
        return tripDao.findById(id);
    }

    @Override
    public void addExcursionToTrip(Trip t, Excursion e) {
        t.addExcursion(e);
        tripDao.update(t);
    }

    @Override
    public Trip findByName(String name) {
        return tripDao.findByName(name);
    }

    @Override
    public List<Trip> findAll() {
        return tripDao.findAll();
    }

    @Override
    public Trip createTrip(Trip t) {
        tripDao.create(t);
        return t;
    }

    @Override
    public Trip updateTrip(Trip t) {
        return tripDao.update(t);
    }

    @Override
    public void removeTrip(Trip t) {
        tripDao.remove(t);
    }

    @Override
    public Long getNumberOfAvailableTripsLeft(Trip t) {
        Long total = t.getAvailableTrips();
        Long reservations = (long) reservationDao.findByTrip(t).size();
        return total - reservations;
    }

    @Override
    public List<Trip> getTripsInDateRange(Date start, Date end) {
        List<Trip> all = tripDao.findAll();
        List<Trip> inRange = new ArrayList<>();
        for (Trip t: all) {
            if ((t.getDateFrom().after(start) || t.getDateFrom().equals(start)) && (t.getDateTo().before(end) || t.getDateTo().equals(end))) {
                inRange.add(t);
            }
        }
        return inRange;
    }
    
    @Override
    public Trip getTripByExcursion(Long excursionId){
        List<Trip> trips = tripDao.findAll();
        
        Excursion ex = excursionDao.findById(excursionId);
        Trip trip = null;
        
        for(Trip t : trips){
            if(t.getExcursions().contains(ex)){
                trip = t;
                break;
            }
        }
        
        return trip;        
    }
}
