package cz.muni.fi.pa165.travelagency.springmvc.controllers;

import cz.muni.fi.pa165.travelagency.dto.ExcursionCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.sampledata.SampleDataLoadingFacadeImpl;
import cz.muni.fi.pa165.travelagency.springmvc.forms.ExcursionCreateDTOValidator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Radovan Sinko
 */
@Controller
@RequestMapping("/shopping/excursion")
public class ExcursionController {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private ExcursionFacade excursionFacade;

    @Autowired
    private UserFacade userFacade;
    
    @Autowired
    private TripFacade tripFacade;
    
    @Autowired
    private ReservationFacade reservationFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listExcursions(Model model, HttpServletRequest req) {
        log.error("request: GET /shopping/excursion/list");
        HttpSession session = req.getSession(true);
        UserDTO user = (UserDTO) session.getAttribute("authUser");
        if (userFacade.isUserAdmin(user.getId())) {
            model.addAttribute("excursions", excursionFacade.getAllExcursions());
        } else {
            model.addAttribute("excursions", excursionFacade.getExcursionById(user.getId()));
        }
        return "/shopping/excursion/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String showExcursion(
            @PathVariable("id") long id,
            Model model) {

        log.error("request: GET /shopping/excursion/view/" + id);
        ExcursionDTO excursion = excursionFacade.getExcursionById(id);
        if (excursion == null) {
            return "redirect:/shopping";
        }
        model.addAttribute("excursion", excursion);
        return "/shopping/excursion/view";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, 
            Model model,
            UriComponentsBuilder uriBuilder,
            RedirectAttributes redirectAttributes) {
        
        ExcursionDTO excursion = excursionFacade.getExcursionById(id);
        
        excursionFacade.deleteExcursion(id);
        log.debug("delete({})", id);
        
        redirectAttributes.addFlashAttribute("alert_success", "Excursion \"" + excursion.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/shopping/excursion/list").toUriString();
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newExcursion(Model model){
        log.error("new()");
        model.addAttribute("excursionCreate", new ExcursionCreateDTO());
        return "shopping/excursion/new";
    }
    
    @ModelAttribute("trips")
    public List<TripDTO> trips(){
        log.debug("trips()");
        return tripFacade.getAllTrips();
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof ExcursionCreateDTO) {
            binder.addValidators(new ExcursionCreateDTOValidator());
        }
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createExcursion(@Valid @ModelAttribute("excursionCreate") ExcursionCreateDTO formBean,
            BindingResult bindingResult,
            Model model, 
            RedirectAttributes redirectAttributes, 
            UriComponentsBuilder uriBuilder){
        
        // TODO do a validator for dateFrom and dateTo
        
        log.error("create(excursionCreate={})", formBean);
        
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "/shopping/excursion/new";
        }
        
        Long id = excursionFacade.createExcursion(formBean);
        
        redirectAttributes.addFlashAttribute("alert_success", "Excursion with " + id + " was created");
        return "redirect:" + uriBuilder.path("/shopping/excursion/view/{id}").buildAndExpand(id).encode().toUriString();
    }
}
