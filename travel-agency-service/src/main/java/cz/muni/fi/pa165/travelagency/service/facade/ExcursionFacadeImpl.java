package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.dto.ExcursionCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.service.BeanMappingService;
import cz.muni.fi.pa165.travelagency.service.CustomerService;
import cz.muni.fi.pa165.travelagency.service.ExcursionService;
import cz.muni.fi.pa165.travelagency.service.ReservationService;
import cz.muni.fi.pa165.travelagency.service.TripService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author omular
 */
@Transactional
@Service
public class ExcursionFacadeImpl implements ExcursionFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private ExcursionService excursionService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TripService tripService;

    @Autowired
    private ReservationService reservationService;

    @Override
    public List<ExcursionDTO> getAllExcursions() {
        return beanMappingService.mapTo(
                excursionService.findAll(),
                ExcursionDTO.class
        );
    }

    @Override
    public ExcursionDTO getExcursionById(Long excursionId) {
        return beanMappingService.mapTo(
                excursionService.findById(excursionId),
                ExcursionDTO.class
        );
    }

    @Override
    public List<ExcursionDTO> getExcursionsByCustomer(Long customerId) {
        List<Reservation> l = reservationService.findByCustomer(
                customerService.findById(customerId)
        );
        List<Excursion> eList = new ArrayList<>();
        for (Reservation r: l) {
            eList.addAll(r.getExcursions());
        }
        return beanMappingService.mapTo(
                eList,
                ExcursionDTO.class
        );
    }

    @Override
    public void deleteExcursion(Long excursionId) {
        excursionService.removeExcursion(new Excursion(excursionId));
    }

    @Override
    public void updateExcursion(ExcursionDTO e) {
        excursionService.updateExcursion(
                beanMappingService.mapTo(e, Excursion.class)
        );
    }

    @Override
    public Long createExcursion(ExcursionCreateDTO e) {
        Excursion excursion = beanMappingService.mapTo(e, Excursion.class);
        excursion = excursionService.createExcursion(excursion);
        Trip t = tripService.findById(e.getTripId());
        tripService.addExcursionToTrip(t, excursion);
        return excursion.getId();
    }
}
