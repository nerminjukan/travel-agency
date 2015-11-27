package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dao.TripDao;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Trip;
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
}
