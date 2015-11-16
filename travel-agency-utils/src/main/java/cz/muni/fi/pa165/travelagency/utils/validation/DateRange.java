package cz.muni.fi.pa165.travelagency.utils.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Validation annotation to validate that 2 dates fields create date range.
 * An array of fields and their matching confirmation fields can be supplied.
 * <br>
 * Supported types: {@link java.sql.Date java.sql.Date}
 * <br>
 * Example, one date range:<br>
 * {@code @DateRange(start="dateFrom", end="dateTo")}<br>
 * <br>
 * Example, multiple date ranges:<br>
 * <code>
 * &#64;DateRange.List({<br>
 * &nbsp;&nbsp;&#64;DateRange(start="dateFrom", end="dateTo"),<br>
 * &nbsp;&nbsp;&#64;DateRange(start="startDate", end="endDate", strict=true)<br>
 * })
 * </code>
 *
 *
 * @author omular
 */
@Target( { TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface DateRange {
    String message() default "{end} should be later than {start}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return Field with start date.
     */
    String start();

    /**
     * @return Field with end date.
     */
    String end();

    /**
     * Define if validation should be strict or not.
     *
     * @return {@code true} if validation will be strict (dates cannot be equal),
     * {@code false} otherwise.
     */
    boolean strict() default false;

    /**
     * Defines several {@code @DateRange} annotations on the same element
     *
     * @see DateRange
     */
    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List
    {
        DateRange[] value();
    }
}