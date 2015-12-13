package cz.muni.fi.pa165.travelagency.sampledata;

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
}
