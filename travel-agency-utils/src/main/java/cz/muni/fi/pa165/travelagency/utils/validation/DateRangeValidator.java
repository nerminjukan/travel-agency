package cz.muni.fi.pa165.travelagency.utils.validation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author omular
 */
public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {
    private String start;
    private String end;
    private boolean strict;

    @Override
    public void initialize(final DateRange constraintAnnotation)
    {
        start = constraintAnnotation.start();
        end = constraintAnnotation.end();
        strict = constraintAnnotation.strict();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext context)
    {
        Date startDate = getDateField(object, start);
        Date endDate = getDateField(object, end);
        if (startDate == null || endDate == null) {
            return false;
        }

        boolean isStartBeforeEnd = startDate.before(endDate);

        if (strict) {
            return isStartBeforeEnd;
        } else {
            return startDate.equals(endDate) || isStartBeforeEnd;
        }
    }

    private Date getDateField(Object obj, String fieldName) {
        Date date;
        Class clazz = obj.getClass();
        try {
            Method dateGetter = clazz.getMethod(
                    getAccessorMethodName(fieldName),
                    new Class[0]
            );
            Object dateGetterResult = dateGetter.invoke(obj);
            
            if (dateGetterResult != null && dateGetterResult instanceof Date) {
                date = (Date) dateGetterResult;
            } else {
                return null;
            }
        } catch (final NoSuchMethodException
                | SecurityException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException ex) {
            return null;
        }
        return date;
    }

    private String getAccessorMethodName(String property){
        StringBuilder builder = new StringBuilder("get");
        builder.append(Character.toUpperCase(property.charAt(0)));
        builder.append(property.substring(1));
        return builder.toString();
    }
}