package cz.muni.fi.pa165.travelagency.springmvc.forms;

import cz.muni.fi.pa165.travelagency.dto.ExcursionUpdateDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Jan Duda
 */
public class ExcursionUpdateDTOValidator implements Validator{
    
    private TripFacade tripFacade;

    @Override
    public boolean supports(Class<?> type) {
        return ExcursionUpdateDTO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExcursionUpdateDTO excursionDTO = (ExcursionUpdateDTO) target;

        Date dateFrom = excursionDTO.getDateFrom();
        Date dateTo = excursionDTO.getDateTo();

        if (dateFrom != null && dateTo != null && dateTo.before(dateFrom)) {
            errors.rejectValue("dateTo", "notbefore.dateTo", "DateTo cannot be before dateFrom.");
        }

        if (excursionDTO.getTripId() == -1) {
            errors.rejectValue("tripId", "notchosen.tripId", "You must choose a trip.");
        } else {
            TripDTO tripDTO = tripFacade.getTripById(excursionDTO.getTripId());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            
            if (dateFrom != null && (tripDTO.getDateFrom().after(dateFrom) || tripDTO.getDateTo().before(dateFrom))) {
                errors.rejectValue("dateFrom", "notbetween.dateFrom", "Date from of excursion cannot be "
                        + "before date from and after date to of trip. "
                        + "Date from of excursion must be between: " + sdf.format(tripDTO.getDateFrom()) + " - " + sdf.format(tripDTO.getDateTo()) + ".");
            }

            if (dateTo != null && (tripDTO.getDateFrom().after(dateTo) || tripDTO.getDateTo().before(dateTo))) {
                errors.rejectValue("dateTo", "notbetween.dateTo", "Date to of excursion cannot be "
                        + "before date from and after date to of trip. "
                        + "Date to of excursion must be between: " + sdf.format(tripDTO.getDateFrom()) + " - " + sdf.format(tripDTO.getDateTo()) + ".");
            }
        }
    }

    public void setTripFacade(TripFacade tripFacade) {
        this.tripFacade = tripFacade;
    }
}
