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

import by.it.model.PayOrder;
import by.it.model.User;
import by.it.service.PayOrderService;
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
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private PayOrderService orderService;

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String personsPage(ModelMap model) {
        fillPersonsModel(model);
        return "admin/persons";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String ordersPage(ModelMap model) {
        fillOrdersModel(model);
        fillPersonsModel(model);
        return "admin/orders";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "admin";
    }

    @RequestMapping(value = "/persons/add", method = RequestMethod.POST)
    public String addPerson(ModelMap model, @Valid User user, BindingResult br) {
        if (!br.hasErrors()) {
            if (user != null) {
                userService.saveNewUser(user);
                model.put("person", user);
            }
        }
        model.put("persons", userService.getAll());
        return "redirect:/admin/persons";
    }

    @RequestMapping(value = "/orders/add", method = RequestMethod.POST)
    public String addOrder(ModelMap model, @Valid PayOrder payOrder, BindingResult br) {
        if (!br.hasErrors()) {
            if (payOrder != null && payOrder.getUser().getId() > 0 && payOrder.getPrice() > 0) {
                orderService.saveNewPayOrder(payOrder);
                model.put("order", payOrder);
            }
        }
        model.put("orders", orderService.getAll());
        return "redirect:/admin/orders";
    }

    @RequestMapping(value = "/persons/delete", method = RequestMethod.POST)
    public String deletePerson(ModelMap model, User user) {
        if (user != null && user.getId() > 0) {
            userService.delete(user);
            model.put("message", "User: " + user.getUserName() + " was deleted");
        }
        fillPersonsModel(model);

        return "redirect:/admin/persons";
    }

    private void fillPersonsModel(ModelMap model) {
        List<User> list = userService.getAll();
        model.put("persons", list);
        User person = new User();
        if (list.size() > 0) {
            person = list.get(0);
        }
        model.put("person", person);
    }

    private void fillOrdersModel(ModelMap model) {
        List<PayOrder> list = orderService.getAll();
        model.put("orders", list);
        PayOrder order = new PayOrder();
//        order.setUser(new User());
        if (list.size() > 0) {
            order = list.get(0);
        }
        model.put("order", order);
    }

    void createAdminIfNeed() {
        userService.createAdminIfNeed();
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

    UserDetails getUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        } else {
            return null;
        }
    }
}
