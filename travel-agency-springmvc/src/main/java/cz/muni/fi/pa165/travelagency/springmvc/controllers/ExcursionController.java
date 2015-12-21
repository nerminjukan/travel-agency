package cz.muni.fi.pa165.travelagency.springmvc.controllers;

import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.sampledata.SampleDataLoadingFacadeImpl;
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

}
