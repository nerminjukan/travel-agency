package cz.muni.fi.pa165.travelagency.springmvc.forms;

import cz.muni.fi.pa165.travelagency.dto.TripUpdateDTO;
import java.util.Date;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TripUpdateDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return TripUpdateDTO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TripUpdateDTO tripDTO = (TripUpdateDTO) target;

        Date dateFrom = tripDTO.getDateFrom();
        Date dateTo = tripDTO.getDateTo();

        if (dateFrom != null && dateTo != null && dateTo.before(dateFrom)) {
            errors.rejectValue("dateTo", "notbefore.dateTo", "DateTo cannot be before dateFrom.");
        }

    }

}