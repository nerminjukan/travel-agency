package cz.muni.fi.pa165.travelagency.springmvc.controllers;

import cz.muni.fi.pa165.travelagency.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.sampledata.SampleDataLoadingFacadeImpl;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Ondrej Mular, Jan Duda
 */
@Controller
@RequestMapping("/shopping/reservation")
public class ReservationController {
    
    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);
    
    @Autowired
    private ReservationFacade reservationFacade;
    
    @Autowired
    private UserFacade userFacade;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listReservations(Model model, HttpServletRequest req) {
        log.error("request: GET /shopping/reservation/list");
        HttpSession session = req.getSession(true);
        UserDTO user = (UserDTO) session.getAttribute("authUser");
        if(userFacade.isUserAdmin(user.getId())){
            model.addAttribute("reservations", reservationFacade.getAllReservations());
        } else {
            model.addAttribute("reservations", reservationFacade.getReservationsByUser(user.getId()));
        }
        return "/shopping/reservation/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String showReservation(
            @PathVariable("id") long id,
            Model model) {
        
        log.error("request: GET /shopping/reservation/view/" + id);
        ReservationDTO r = reservationFacade.getReservationById(id);
        if (r == null) {
            return "redirect:/shopping";
        }
        model.addAttribute("reservation", r);
        return "/shopping/reservation/view";
    }
}
