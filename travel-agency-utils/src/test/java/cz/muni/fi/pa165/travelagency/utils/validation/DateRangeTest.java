package cz.muni.fi.pa165.travelagency.utils.validation;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author omular
 */
public class DateRangeTest {
    private Validator validator;
    private TestClassStrict strict;
    private TestClassNonStrict nonStrict;
    private Set<ConstraintViolation<TestClass>> violations;
    private LocalDate date = LocalDate.of(2015, 1, 1);

    @BeforeMethod
    public void setUpMethod() throws Exception {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
        strict = new TestClassStrict();
        nonStrict = new TestClassNonStrict();
    }

    @Test
    public void testStrictBothNull() {
        violations = validator.validate(strict);
        Assert.assertEquals(violations.size(), 1);
    }

    @Test
    public void testStrictStartNull() {
        strict.setEnd(Date.valueOf(date));
        violations = validator.validate(strict);
        Assert.assertEquals(violations.size(), 1);
    }

    @Test
    public void testStrictEndNull() {
        strict.setStart(Date.valueOf(date));
        violations = validator.validate(strict);
        Assert.assertEquals(violations.size(), 1);
    }

    @Test
    public void testStrictInValid() {
        strict.setStart(Date.valueOf(date.plusDays(1)));
        strict.setEnd(Date.valueOf(date));
        violations = validator.validate(strict);
        Assert.assertEquals(violations.size(), 1);
    }

    @Test
    public void testStrictSameDay() {
        strict.setStart(Date.valueOf(date));
        strict.setEnd(Date.valueOf(date));
        violations = validator.validate(strict);
        Assert.assertEquals(violations.size(), 1);
    }

    @Test
    public void testStrictValid() {
        strict.setStart(Date.valueOf(date));
        strict.setEnd(Date.valueOf(date.plusDays(1)));
        violations = validator.validate(strict);
        Assert.assertEquals(violations.size(), 0);
    }

    @Test
    public void testNonStrictBothNull() {
        violations = validator.validate(nonStrict);
        Assert.assertEquals(violations.size(), 1);
    }

    @Test
    public void testNonStrictStartNull() {
        nonStrict.setEnd(Date.valueOf(date));
        violations = validator.validate(nonStrict);
        Assert.assertEquals(violations.size(), 1);
    }

    @Test
    public void testNonStrictEndNull() {
        nonStrict.setStart(Date.valueOf(date));
        violations = validator.validate(nonStrict);
        Assert.assertEquals(violations.size(), 1);
    }

    @Test
    public void testNonStrictInValid() {
        nonStrict.setStart(Date.valueOf(date.plusDays(1)));
        nonStrict.setEnd(Date.valueOf(date));
        violations = validator.validate(nonStrict);
        Assert.assertEquals(violations.size(), 1);
    }

    @Test
    public void testNonStrictSameDay() {
        nonStrict.setStart(Date.valueOf(date));
        nonStrict.setEnd(Date.valueOf(date));
        violations = validator.validate(nonStrict);
        Assert.assertEquals(violations.size(), 0);
    }

    @Test
    public void testNonStrictValid() {
        nonStrict.setStart(Date.valueOf(date));
        nonStrict.setEnd(Date.valueOf(date.plusDays(1)));
        violations = validator.validate(nonStrict);
        Assert.assertEquals(violations.size(), 0);
    }

    @DateRange(start="start", end="end", strict=true)
    private class TestClassStrict extends TestClass {}

    @DateRange(start="start", end="end")
    private class TestClassNonStrict extends TestClass {}

    private class TestClass {
        private Date start;
        private Date end;

        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }
        
    }
}
