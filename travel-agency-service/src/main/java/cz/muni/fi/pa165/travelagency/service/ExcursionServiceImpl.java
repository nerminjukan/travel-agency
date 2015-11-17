package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dao.ExcursionDao;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author omular
 */
@Service
public class ExcursionServiceImpl implements ExcursionService {

    @Autowired
    private ExcursionDao excursionDao;

    @Override
    public Excursion findById(Long excursionId) {
        return excursionDao.findById(excursionId);
    }

    @Override
    public List<Excursion> findAll() {
        return excursionDao.findAll();
    }

    @Override
    public Excursion createExcursion(Excursion e) {
        excursionDao.create(e);
        return e;
    }

    @Override
    public Excursion updateExcusion(Excursion e) {
        return excursionDao.update(e);
    }

    @Override
    public void deleteExcursion(Excursion e) {
        excursionDao.remove(e);
    }

    @Override
    public Excursion findByName(String name) {
        return excursionDao.findByName(name);
    }
}
