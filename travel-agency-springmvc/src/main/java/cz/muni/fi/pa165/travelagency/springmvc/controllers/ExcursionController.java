package cz.muni.fi.pa165.travelagency.springmvc.controllers;

import cz.muni.fi.pa165.travelagency.dto.ExcursionCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.dto.ExcursionUpdateDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.sampledata.SampleDataLoadingFacadeImpl;
import cz.muni.fi.pa165.travelagency.springmvc.forms.ExcursionCreateDTOValidator;
import cz.muni.fi.pa165.travelagency.springmvc.forms.ExcursionUpdateDTOValidator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
@RequestMapping("/admin/excursion")
public class ExcursionController {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private ExcursionFacade excursionFacade;

    @Autowired
    private UserFacade userFacade;
    
    @Autowired
    private TripFacade tripFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listExcursions(Model model, HttpServletRequest req) {
        log.error("request: GET /admin/excursion/list");
        HttpSession session = req.getSession(true);
        UserDTO user = (UserDTO) session.getAttribute("authUser");
        if (userFacade.isUserAdmin(user.getId())) {
            model.addAttribute("excursions", excursionFacade.getAllExcursions());
        } else {
            model.addAttribute("excursions", excursionFacade.getExcursionById(user.getId()));
        }
        return "/admin/excursion/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String showExcursion(
            @PathVariable("id") long id,
            Model model) {

        log.error("request: GET /admin/excursion/view/" + id);
        ExcursionDTO excursion = excursionFacade.getExcursionById(id);
        if (excursion == null) {
            return "redirect:/shopping";
        }
        model.addAttribute("excursion", excursion);
        return "/admin/excursion/view";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable("id") long id, 
            Model model,
            UriComponentsBuilder uriBuilder,
            RedirectAttributes redirectAttributes) {
        
        ExcursionDTO excursion = excursionFacade.getExcursionById(id);
        
        excursionFacade.deleteExcursion(id);
        log.debug("delete({})", id);
        
        redirectAttributes.addFlashAttribute("alert_success", "Excursion \"" + excursion.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/admin/excursion/list").toUriString();
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newExcursion(Model model){
        log.error("new()");
        model.addAttribute("excursionCreate", new ExcursionCreateDTO());
        return "/admin/excursion/new";
    }
    
    @ModelAttribute("trips")
    public List<TripDTO> trips(){
        log.debug("trips()");
        return tripFacade.getAllTrips();
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
        if (binder.getTarget() instanceof ExcursionCreateDTO) {
            ExcursionCreateDTOValidator validator = new ExcursionCreateDTOValidator();
            validator.setTripFacade(tripFacade);
            binder.addValidators(validator);
        }
        if (binder.getTarget() instanceof ExcursionUpdateDTO){
            ExcursionUpdateDTOValidator validator = new ExcursionUpdateDTOValidator();
            validator.setTripFacade(tripFacade);
            binder.addValidators(validator);
        }
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createExcursion(@Valid @ModelAttribute("excursionCreate") ExcursionCreateDTO formBean,
            BindingResult bindingResult,
            Model model, 
            RedirectAttributes redirectAttributes, 
            UriComponentsBuilder uriBuilder){
        
        log.error("create(excursionCreate={})", formBean);
        
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.error("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.error("FieldError: {}", fe);
            }
            return "/admin/excursion/new";
        }
        
        Long id = excursionFacade.createExcursion(formBean);
        
        redirectAttributes.addFlashAttribute("alert_success", "Excursion with " + id + " was created");
        return "redirect:" + uriBuilder.path("/admin/excursion/view/{id}").buildAndExpand(id).encode().toUriString();
    }
    
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String prepareForUpdate(@PathVariable("id") long id,
            Model model,
            UriComponentsBuilder uriBuilder) {
        
        log.error("updateExcursion(id={})", id);
        
        Long tripId = tripFacade.getTripByExcursion(id).getId();
        ExcursionUpdateDTO excursionToUpdate = new ExcursionUpdateDTO(excursionFacade.getExcursionById(id), tripId);
        
        model.addAttribute("excursionUpdate", excursionToUpdate);
        return "/admin/excursion/update";
    }
    
    @RequestMapping(value = "/updating", method = RequestMethod.POST)
    public String updateExcursion(@Valid @ModelAttribute("excursionUpdate") ExcursionUpdateDTO formBean,
            BindingResult bindingResult,
            Model model, 
            RedirectAttributes redirectAttributes, 
            UriComponentsBuilder uriBuilder) {
        
        log.error("update(excursionUpdate={})", formBean);
        
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.error("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.error("FieldError: {}", fe);
            }
            return "/admin/excursion/update";
        }
        
        excursionFacade.updateExcursion(formBean);
        
        redirectAttributes.addFlashAttribute("alert_success", "Excursion with " + formBean.getId() + " was updated");
        return "redirect:" + uriBuilder.path("/admin/excursion/view/{id}").buildAndExpand(formBean.getId()).encode().toUriString();
    }
}
