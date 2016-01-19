package cz.muni.fi.pa165.travelagency.springmvc.controllers;

import cz.muni.fi.pa165.travelagency.dto.ReservationDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import static cz.muni.fi.pa165.travelagency.springmvc.controllers.ReservationController.log;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Jan Duda
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {
    
    final static Logger log = LoggerFactory.getLogger(ShoppingController.class);

    @Autowired
    TripFacade tripFacade;

    @Autowired
    ExcursionFacade excursionFacade;

    @Autowired
    ReservationFacade reservationFacade;
    
    @Autowired
    UserFacade userFacade;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listUsers(Model model){
        
        log.error("request: GET /admin/user/list");
        
        model.addAttribute("users", userFacade.getAllUsers());
        
        return "/admin/user/list";
    }
    
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String showUser(@PathVariable("id") long id, Model model){
        
        log.error("request: GET /admin/user/view/" + id);
        
        UserDTO user = userFacade.getUserById(id);
        if(user == null){
            return "redirect:/shopping";
        }
        user.setIsAdmin(userFacade.isUserAdmin(id));
        List<ReservationDTO> usersReservations = reservationFacade.getReservationsByUser(id);
        if(usersReservations == null){
            usersReservations = new ArrayList<>();
        }
        
        model.addAttribute("user", user);
        model.addAttribute("usersReservations", usersReservations);
        return "/admin/user/view";
    }
}
