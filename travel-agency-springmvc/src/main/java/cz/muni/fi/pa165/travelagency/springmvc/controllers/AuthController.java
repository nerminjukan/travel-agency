package cz.muni.fi.pa165.travelagency.springmvc.controllers;

import cz.muni.fi.pa165.travelagency.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.travelagency.dto.UserDTO;
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
@RequestMapping("/auth")
public class AuthController {

    final static Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserFacade userFacade;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String authForm(
            Model model,
            HttpServletRequest req,
            HttpServletResponse res) {
        log.error("request: GET /auth/login");
        HttpSession session = req.getSession(true);
        if (session.getAttribute("authUser") != null) {
            return "redirect:/shopping";
        }
        return "auth/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authenticate(
            @RequestParam String email,
            @RequestParam String password,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req,
            HttpServletResponse res) {
        log.error("request: POST /auth/login");
        UserAuthenticateDTO authDTO = new UserAuthenticateDTO();
        authDTO.setEmail(email);
        authDTO.setPassword(password);
        UserDTO user = userFacade.authUser(authDTO);
        if (user == null) {
            redirectAttributes.addFlashAttribute("alert_info", "Wrong email or password");
            return "redirect:/auth/login";
        }
        HttpSession session = req.getSession(true);
        user.setIsAdmin(userFacade.isUserAdmin(user.getId()));
        session.setAttribute("authUser", user);
        redirectAttributes.addFlashAttribute("alert_info", "You have been logged in.");
        return "redirect:/shopping";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req) {
        log.error("request: /auth/logout");
        HttpSession session = req.getSession(true);
        session.removeAttribute("authUser");
        redirectAttributes.addFlashAttribute("alert_info", "You have been successfully logged out.");
        return "redirect:/auth/login";
    }
}
