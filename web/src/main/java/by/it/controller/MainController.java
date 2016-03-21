package by.it.controller;

import by.it.model.enums.UserProfileType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @Autowired
    private AdminController personController;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String homePage(ModelMap model) {
        String direction = "welcome";

        UserDetails details = personController.getUserDetails();

        if (details != null) {
            SimpleGrantedAuthority adminRole = new SimpleGrantedAuthority("ROLE_" + UserProfileType.ADMIN.getType());
            SimpleGrantedAuthority userRole = new SimpleGrantedAuthority("ROLE_" + UserProfileType.USER.getType());
            if (details.getAuthorities().contains(adminRole)) {
                return "redirect:admin";
            } else if (details.getAuthorities().contains(userRole)) {
                return "redirect:user";
            }
        }
        model.addAttribute("greeting", "Hello and welcome");
        return direction;
    }

    @RequestMapping(value = "/access_denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", personController.getPrincipal());
        return "access-denied";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        personController.createAdminIfNeed();

        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

}
