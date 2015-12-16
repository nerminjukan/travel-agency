package cz.muni.fi.pa165.travelagency.sampledata;

import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Trip;
import cz.muni.fi.pa165.travelagency.entity.User;
import cz.muni.fi.pa165.travelagency.service.ExcursionService;
import cz.muni.fi.pa165.travelagency.service.ReservationService;
import cz.muni.fi.pa165.travelagency.service.TripService;
import cz.muni.fi.pa165.travelagency.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;


@Component
@Transactional 
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private TripService tripService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ExcursionService excursionService;

    @Override
    public void loadData() throws IOException {
        user("test@mail.com", "JAnko Mrkvicka", "+123456789", "password");
        user("admin@mail.com", "Administrator", "+1234567890", "admin");
        Set<Excursion> exSet = new HashSet<>();
        exSet.add(excursion(
                "Test excursion",
                "Long description :D",
                Date.valueOf(LocalDate.of(2015, 10, 3)),
                Date.valueOf(LocalDate.of(2015, 10, 5)),
                "Another great destination",
                new BigDecimal("59.99"))
        );
        exSet.add(excursion(
                "Another excursion",
                "Even longer description :D",
                Date.valueOf(LocalDate.of(2015, 10, 4)),
                Date.valueOf(LocalDate.of(2015, 10, 5)),
                "Another great destination",
                new BigDecimal("70.99"))
        );
        trip(
                "Best trip",
                "",
                Date.valueOf(LocalDate.of(2015, 10, 1)),
                Date.valueOf(LocalDate.of(2015, 10, 20)),
                "New destination",
                new BigDecimal("199.00"),
                exSet,
                10L
        );
        trip(
                "Another trip",
                "boring trip",
                Date.valueOf(LocalDate.of(2015, 12, 24)),
                Date.valueOf(LocalDate.of(2015, 12, 24)),
                "Home",
                new BigDecimal("39.00"),
                new HashSet<Excursion>(),
                100L
        );
    }

    private User user(String email, String name, String phoneNumber, String password) {
        User u = new User();
        u.setEmail(email);
        u.setName(name);
        u.setPhoneNumber(phoneNumber);
        if (password.equals("admin")) {
            u.setIsAdmin(true);
        }
        userService.registerUser(u, password);
        return u;
    }

    private Trip trip(
            String name,
            String desc,
            Date dateFrom,
            Date dateTo,
            String destination,
            BigDecimal price,
            Set<Excursion> excursions,
            Long availableTrips) {
        Trip t = new Trip();
        t.setName(name);
        t.setDescription(desc);
        t.setDateFrom(dateFrom);
        t.setDateTo(dateTo);
        t.setDestination(destination);
        t.setPrice(price);
        for (Excursion e: excursions) {
            t.addExcursion(e);
        }
        t.setAvailableTrips(availableTrips);
        tripService.createTrip(t);
        return t;
    }

    private Excursion excursion(
            String name,
            String desc,
            Date dateFrom,
            Date dateTo,
            String destination,
            BigDecimal price) {
        Excursion e = new Excursion();
        e.setName(name);
        e.setDescription(desc);
        e.setDateFrom(dateFrom);
        e.setDateTo(dateTo);
        e.setDestination(destination);
        e.setPrice(price);
        excursionService.createExcursion(e);
        return e;
    }
}
