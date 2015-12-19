package cz.muni.fi.pa165.travelagency.sampledata;

import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.entity.Reservation;
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
        User admin = user("admin@mail.com", "Administrator", "+1234567890", "admin");
        User u1 = user("test@mail.com", "JAnko Mrkvicka", "+123456789", "password");
        User u2 = user("test2@mail.com", "Petr Koroptvicka", "+999222333", "pass");
        User u3 = user("test3@mail.com", "Jana Nadenikova", "+444666322", "pass");
        
        Excursion ex1 = excursion(
                "Test excursion",
                "Long description :D",
                Date.valueOf(LocalDate.of(2015, 10, 3)),
                Date.valueOf(LocalDate.of(2015, 10, 5)),
                "Another great destination",
                new BigDecimal("59.99")
        );
        Excursion ex2 = excursion(
                "Another excursion",
                "Even longer description :D",
                Date.valueOf(LocalDate.of(2015, 10, 4)),
                Date.valueOf(LocalDate.of(2015, 10, 5)),
                "Another great destination",
                new BigDecimal("70.99")
        );
        Set<Excursion> exSet = new HashSet<>();
        exSet.add(ex1);
        exSet.add(ex2);
        
        Trip t1 = trip(
                "Best trip",
                "",
                Date.valueOf(LocalDate.of(2015, 10, 1)),
                Date.valueOf(LocalDate.of(2015, 10, 20)),
                "New destination",
                new BigDecimal("199.00"),
                exSet,
                10L
        );
        Trip t2 = trip(
                "Another trip",
                "boring trip",
                Date.valueOf(LocalDate.of(2015, 12, 24)),
                Date.valueOf(LocalDate.of(2015, 12, 24)),
                "Home",
                new BigDecimal("39.00"),
                new HashSet<Excursion>(),
                100L
        );
        
        Set<Excursion> exSetForUser1 = new HashSet<>();
        exSetForUser1.add(ex1);
        reservation(u1, t1, exSetForUser1);
        reservation(u2, t2, new HashSet<Excursion>());
        reservation(u3, t2, exSet);
    }

    private User user(String email, String name, String phoneNumber, String password) {
        User u = new User();
        u.setEmail(email);
        u.setName(name);
        u.setPhoneNumber(phoneNumber);
        if (password.equals("admin")) {
            u.setIsAdmin(true);
        } else {
            u.setIsAdmin(false);
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
    
    private Reservation reservation(
            User user,
            Trip trip,
            Set<Excursion> excursion){
        
        Reservation r = new Reservation();
        r.setTrip(trip);
        r.setUser(user);
        for (Excursion e : excursion) {
            r.addExcursion(e);
        }
        
        reservationService.createReservation(r);
        return r;
    }
}
