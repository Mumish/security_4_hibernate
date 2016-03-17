/*
 * Copyright (C) 2015 GHX, Inc.
 *  Louisville, Colorado, USA.
 *  All rights reserved.
 *
 *  Warning: Unauthorized reproduction or distribution of this program, or
 *  any portion of it, may result in severe civil and criminal penalties,
 *  and will be prosecuted to the maximum extent possible under the law.
 *
 *  Created on 012 12.01.2015
 */
package by.it.controller;

//import by.it.academy.pojos.Person;
//import by.it.academy.services.IPersonService;
import by.it.model.User;
import by.it.model.UserProfile;
import by.it.model.enums.UserProfileType;
import by.it.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class PersonController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String mainPage(ModelMap model) {
        fillModel(model);
//        return "persons/main";
        return "admin/persons";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "admin";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPerson(ModelMap model, @Valid User person, BindingResult br) {
        if(!br.hasErrors()) {
            if (person != null) {
                userService.persist(person);
                model.put("person", person);
            }
        }
        model.put("persons", userService.getAll());
        return "admin";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePerson(ModelMap model, User person) {
        if (person != null) {
            userService.delete(person);
            model.put("message", "Person: " + person.getUserName() + " was deleted");
        }
        fillModel(model);

        return "admin";
    }
    
    private void fillModel(ModelMap model) {
        List<User> list = userService.getAll();
        model.put("persons", list);
        User person = new User();
        if (list.size() > 0) {
            person = list.get(0);
        }
        model.put("person", person);
    }
    void createAdminIfNeed() {

        User u = userService.findById(1L);

        if (u == null) {

            u = new User();

            u.setUserName("root");

            u.setPassword("root");

            u.setFirstName("admin_first_name");

            u.setLastName("admin_last_name");

            u.setEmail("admin@email.com");

            UserProfile profAdmin = new UserProfile();
            profAdmin.setType(UserProfileType.ADMIN.getType());
            u.getUserProfiles().add(profAdmin);
            UserProfile profDba = new UserProfile();
            profDba.setType(UserProfileType.DBA.getType());
            u.getUserProfiles().add(profDba);

            userService.persist(u);
        }

    }
    
    String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
