package cz.muni.fi.pa165.travelagency.springmvc.controllers;

import cz.muni.fi.pa165.travelagency.dto.TripCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.dto.TripUpdateDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.sampledata.SampleDataLoadingFacadeImpl;
import cz.muni.fi.pa165.travelagency.springmvc.forms.TripCreateDTOValidator;
import cz.muni.fi.pa165.travelagency.springmvc.forms.TripUpdateDTOValidator;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.validation.Valid;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * @author Ondrej Glasnak
 */
@Controller
@RequestMapping("/admin/trip")
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
        log.error("request: GET /admin/trip/list");
        HttpSession session = req.getSession(true);
        UserDTO user = (UserDTO) session.getAttribute("authUser");
        if (userFacade.isUserAdmin(user.getId())) {
            model.addAttribute("trips", tripFacade.getAllTrips());
        } else {
            model.addAttribute("trips", tripFacade.getTripsByUser(user.getId()));
        }
        return "/admin/trip/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String showTrip(
            @PathVariable("id") long id,
            Model model) {

        log.error("request: GET /admin/trip/view/" + id);
        TripDTO trip = tripFacade.getTripById(id);
        if (trip == null) {
            return "redirect:/shopping";
        }
        model.addAttribute("trip", trip);
        return "/admin/trip/view";
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
        return "redirect:" + uriBuilder.path("/admin/trip/list").toUriString();
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newTrip(Model model){
        log.error("new()");
        model.addAttribute("tripCreate", new TripCreateDTO());
        return "admin/trip/new";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(
            @PathVariable long id,
            Model model) {

        TripDTO tripToUpdate = tripFacade.getTripById(id);
        if (tripToUpdate == null) {
            return "redirect:/admin/trip/list";
        }
        model.addAttribute("tripUpdate", tripToUpdate);
        return "admin/trip/update";
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
        if (binder.getTarget() instanceof TripCreateDTO) {
            TripCreateDTOValidator validator = new TripCreateDTOValidator();
            binder.addValidators(validator);
        }
        if (binder.getTarget() instanceof TripUpdateDTO) {
            TripUpdateDTOValidator validator = new TripUpdateDTOValidator();
            binder.addValidators(validator);
        }
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createTrip(@Valid @ModelAttribute("tripCreate") TripCreateDTO formBean,
            BindingResult bindingResult,
            Model model, 
            RedirectAttributes redirectAttributes, 
            UriComponentsBuilder uriBuilder) {
        
        log.error("create(tripCreate={})", formBean);
        
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.error("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.error("FieldError: {}", fe);
            }
            return "/admin/trip/new";
        }
        
        Long id = tripFacade.createTrip(formBean);
        
        redirectAttributes.addFlashAttribute("alert_success", "Trip with " + id + " was created");
        return "redirect:" + uriBuilder.path("/admin/trip/view/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/updating", method = RequestMethod.POST)
    public String updateTrip(
            @Valid @ModelAttribute("tripUpdate") TripUpdateDTO formBean,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder) {

        log.error("update(tripUpdate={})", formBean);

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.error("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.error("FieldError: {}", fe);
            }
            return "/admin/trip/update";
        }

        tripFacade.updateTrip(formBean);

        redirectAttributes.addFlashAttribute(
                "alert_success", "Trip " + formBean.getId() + " was updated"
        );
        return "redirect:" + uriBuilder.path("/admin/trip/view/{id}").buildAndExpand(formBean.getId()).encode().toUriString();
    }
}


