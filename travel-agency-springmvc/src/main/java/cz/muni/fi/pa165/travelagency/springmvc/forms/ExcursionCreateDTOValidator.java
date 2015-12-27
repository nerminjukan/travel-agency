package cz.muni.fi.pa165.travelagency.springmvc.forms;

import cz.muni.fi.pa165.travelagency.dto.ExcursionCreateDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Jan Duda
 */
public class ExcursionCreateDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return ExcursionCreateDTO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExcursionCreateDTO excursionCreateDTO = (ExcursionCreateDTO) target;
        if(excursionCreateDTO.getPrice() == null) return;
        if(excursionCreateDTO.getPrice().longValue() < 0.0){
            errors.rejectValue("price", "ExcursionCreateDTOValidator.price.negative");
        }
    }
    
}
