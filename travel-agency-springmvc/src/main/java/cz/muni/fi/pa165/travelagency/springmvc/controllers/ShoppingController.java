package cz.muni.fi.pa165.travelagency.springmvc.controllers;

import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.exceptions.TravelAgencyServiceException;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    final static Logger log = LoggerFactory.getLogger(ShoppingController.class);

    @Autowired
    TripFacade tripFacade;

    @Autowired
    ExcursionFacade excursionFacade;

    @Autowired
    ReservationFacade reservationFacade;

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
        ReservationCreateDTO dto = new ReservationCreateDTO();
        dto.setTripId(id);
        model.addAttribute("createReservation", dto);
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

    @RequestMapping(value = "/reservate", method = RequestMethod.POST)
    public String reservate(
            @ModelAttribute("createReservation") ReservationCreateDTO formBean,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder,
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        log.debug("create(productCreate={})", formBean);

        HttpSession session = req.getSession(true);
        
        try {
            Long id = reservationFacade.createReservation(formBean, (UserDTO) session.getAttribute("authUser"));
            redirectAttributes.addFlashAttribute("alert_success", "Reservation " + id + " was created");
            return "redirect:" + uriBuilder.path("/shopping/reservation/view/{id}").buildAndExpand(id).encode().toUriString();
        } catch (TravelAgencyServiceException e) {
            redirectAttributes.addFlashAttribute("alert_success", "Unable to create reservation: " + e.getMessage());
        }
        return "redirect:" + uriBuilder.path("/shopping").build().encode().toUriString();
    }
}
