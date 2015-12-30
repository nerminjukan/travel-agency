package cz.muni.fi.pa165.travelagency.springmvc.controllers;

import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.sampledata.SampleDataLoadingFacadeImpl;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * @author Ondrej Glasnak
 */
@Controller
@RequestMapping("/shopping/trip")
public class TripController {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private TripFacade tripFacade;

    @Autowired
    private UserFacade userFacade;
    
    @Autowired
    private ExcursionFacade excursionFacade;
    
    @Autowired
    private ReservationFacade reservationFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listTrips(Model model, HttpServletRequest req) {
        log.error("request: GET /shopping/trip/list");
        HttpSession session = req.getSession(true);
        UserDTO user = (UserDTO) session.getAttribute("authUser");
        if (userFacade.isUserAdmin(user.getId())) {
            model.addAttribute("trips", tripFacade.getAllTrips());
        } else {
            model.addAttribute("trips", tripFacade.getTripsByUser(user.getId()));
        }
        return "/shopping/trip/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String showTrip(
            @PathVariable("id") long id,
            Model model) {

        log.error("request: GET /shopping/trip/view/" + id);
        TripDTO trip = tripFacade.getTripById(id);
        if (trip == null) {
            return "redirect:/shopping";
        }
        model.addAttribute("trip", trip);
        return "/shopping/trip/view";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, 
            Model model,
            UriComponentsBuilder uriBuilder,
            RedirectAttributes redirectAttributes) {
        
        TripDTO trip = tripFacade.getTripById(id);
        
        tripFacade.deleteTrip(id);
        log.debug("delete({})", id);
        
        redirectAttributes.addFlashAttribute("alert_success", "Trip \"" + trip.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/shopping/trip/list").toUriString();
    }
}