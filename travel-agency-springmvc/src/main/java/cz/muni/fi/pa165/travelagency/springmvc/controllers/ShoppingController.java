package cz.muni.fi.pa165.travelagency.springmvc.controllers;

import cz.muni.fi.pa165.travelagency.dao.TripDaoImpl;
import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    final static Logger log = LoggerFactory.getLogger(ShoppingController.class);

    @Autowired
    TripFacade tripFacade;

    @Autowired
    ExcursionFacade excursionFacade;

    @RequestMapping(method = RequestMethod.GET)
    public String show(Model model) {
        log.error("request: GET /shopping");
        model.addAttribute("trips", tripFacade.getAllTrips());
        return "shopping/list";
    }

    @RequestMapping(value = "/trip/{id}", method = RequestMethod.GET)
    public String showTrip(@PathVariable("id") long id, Model model) {
        log.error("request: GET /shopping/trip/" + id);
        TripDTO t = tripFacade.getTripById(id);
        if (t == null) {
            return "redirect:/shopping";
        }
        model.addAttribute("trip", t);
        return "shopping/trip";
    }

    @RequestMapping(value = "/excursion/{id}", method = RequestMethod.GET)
    public String showExcursion(@PathVariable("id") long id, Model model) {
        log.error("request: GET /shopping/excursion/" + id);
        ExcursionDTO t = excursionFacade.getExcursionById(id);
        if (t == null) {
            return "redirect:/shopping";
        }
        model.addAttribute("excursion", t);
        return "shopping/excursion";
    }
}
