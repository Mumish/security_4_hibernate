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
import by.it.service.AccountService;
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
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String userPage(ModelMap model) {
        String userName = getPrincipal();
        model.addAttribute("userName", userName);
        User user = userService.findByUserName(userName);
        List<PayOrder> orders = new ArrayList<>();
        List<PayOrder> payedOrders = new ArrayList<>();

        for (PayOrder order : user.getOrders()) {

            if (order.getPayment() == null) {
                orders.add(order);
            } else {
                payedOrders.add(order);
            }
        }

        user.setOrders(orders);

        model.addAttribute("user", user);
        if (user.getOrders().size() > 0) {
            model.addAttribute("order", user.getOrders().get(0));
        } else {
            model.addAttribute("order", new PayOrder());
        }

        model.addAttribute("payedOrders", payedOrders);

        fillPersonsModel(model);

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

    @RequestMapping(value = "/lock", method = RequestMethod.POST)
    public String lockCard(ModelMap model, @Valid User user, BindingResult br) {
        if (user != null && user.getId() > 0) {
            user = userService.findById(user.getId());
            cardsService.lockCardByUser(user.getCreditCard());
            model.put("message", "CreditCard: " + user.getCreditCard().getNum() + " was locked");
        }

        return "redirect:/user";
    }

    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    public String unlockCard(ModelMap model, @Valid User user, BindingResult br) {
        if (user != null && user.getId() > 0) {
            user = userService.findById(user.getId());
            cardsService.unlockCardByUser(user.getCreditCard());
            model.put("message", "CreditCard: " + user.getCreditCard().getNum() + " was unlocked");
        }

        return "redirect:/user";
    }

    @RequestMapping(value = "/annul", method = RequestMethod.POST)
    public String annulAccount(ModelMap model, @Valid User user, BindingResult br) {
        if (user != null && user.getId() > 0) {
            user = userService.findById(user.getId());
            accountService.annulAccountByUser(user.getAccount());
            model.put("message", "Account: " + user.getCreditCard().getNum() + " was annuled");
        }

        return "redirect:/user";
    }

    @RequestMapping(value = "/active", method = RequestMethod.POST)
    public String activeAccount(ModelMap model, @Valid User user, BindingResult br) {
        if (user != null && user.getId() > 0) {
            user = userService.findById(user.getId());
            accountService.activeAccountByUser(user.getAccount());
            model.put("message", "Account: " + user.getCreditCard().getNum() + " was actived");
        }

        return "redirect:/user";
    }

    @RequestMapping(value = "/trans", method = RequestMethod.POST)
    public String transfer(ModelMap model, @Valid PayOrder payOrder, BindingResult br) {
        if (!br.hasErrors()) {
            if (payOrder != null && payOrder.getUser().getId() > 0 && payOrder.getId() > 0 && payOrder.getPrice() > 0) {
                userService.transfer(payOrder);
            }
        }
        return "redirect:/user";
    }

    private void fillPersonsModel(ModelMap model) {
        List<User> list = userService.getAll();

        User user = (User) model.get("user");
        list.remove(user);
        model.put("persons", list);
        User person = new User();
        if (list.size() > 0) {
            person = list.get(0);
        }
        model.put("person", person);
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
