package cz.muni.fi.pa165.travelagency.springmvc.forms;

import cz.muni.fi.pa165.travelagency.dto.TripCreateDTO;
import java.util.Date;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Jan Duda
 */
public class TripCreateDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return TripCreateDTO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TripCreateDTO tripCreateDTO = (TripCreateDTO) target;

        Date dateFrom = tripCreateDTO.getDateFrom();
        Date dateTo = tripCreateDTO.getDateTo();

        if (dateFrom != null && dateTo != null && dateTo.before(dateFrom)) {
            errors.rejectValue("dateTo", "notbefore.dateTo", "DateTo cannot be before dateFrom.");
        }

    }

}