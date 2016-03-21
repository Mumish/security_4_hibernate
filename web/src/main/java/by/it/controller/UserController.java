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

import by.it.model.CreditCard;
import by.it.model.PayOrder;
import by.it.model.User;
import by.it.service.CreditCardService;
import by.it.service.PayOrderService;
import by.it.service.UserService;
import java.util.ArrayList;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PayOrderService orderService;
    @Autowired
    private CreditCardService cardsService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String userPage(ModelMap model) {
        String userName = getPrincipal();
        model.addAttribute("userName", userName);
        User user = userService.findByUserName(userName);
        List<PayOrder> orders = new ArrayList<>();

        for (PayOrder order : user.getOrders()) {

            if (order.getPayment() == null) {
                orders.add(order);
            }
        }

        user.setOrders(orders);

        model.addAttribute("user", user);
        if (user.getOrders().size() > 0) {
            model.addAttribute("order", user.getOrders().get(0));
        }

        return "user";
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String payOrder(ModelMap model, @Valid PayOrder payOrder, BindingResult br) {
        if (!br.hasErrors()) {
            if (payOrder != null && payOrder.getId() > 0) {
                payOrder = orderService.findById(payOrder.getId());
                if (payOrder.getUser().getCreditCard().getStatusId() == 0 && payOrder.getPayment() == null) {
                    userService.payOrder(payOrder);
                }
            }
        }
        return "redirect:/user";
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

    @RequestMapping(value = "/cards/lock", method = RequestMethod.POST)
    public String lockCard(ModelMap model, @Valid CreditCard creditCard, BindingResult br) {
        if (creditCard != null && creditCard.getCreditCardId() > 0) {
            cardsService.lockCard(creditCard);
            model.put("message", "Order: " + creditCard.getNum() + " was locked");
        }
        fillOrdersModel(model);

        return "redirect:/admin/cards";
    }

    @RequestMapping(value = "/cards/unlock", method = RequestMethod.POST)
    public String unlockCard(ModelMap model, @Valid CreditCard creditCard, BindingResult br) {
        if (creditCard != null && creditCard.getCreditCardId() > 0) {
            cardsService.unlockCard(creditCard);
            model.put("message", "Order: " + creditCard.getNum() + " was unlocked");
        }
        fillOrdersModel(model);

        return "redirect:/admin/cards";
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

    @RequestMapping(value = "/orders/delete", method = RequestMethod.POST)
    public String deletePerson(ModelMap model, PayOrder order) {
        if (order != null && order.getId() > 0) {
            orderService.delete(order);
            model.put("message", "Order: " + order.getNum() + " was deleted");
        }
        fillOrdersModel(model);

        return "redirect:/admin/orders";
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

    private void fillCardsModel(ModelMap model) {
        List<CreditCard> list = cardsService.lockedCardList();
        model.put("lockedCards", list);
        CreditCard card = new CreditCard();
//        order.setUser(new User());
        if (list.size() > 0) {
            card = list.get(0);
        }
        model.put("card", card);

        list = cardsService.forLockCardList();
        model.put("forLockCards", list);

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
