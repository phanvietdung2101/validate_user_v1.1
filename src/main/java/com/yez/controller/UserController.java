package com.yez.controller;


import com.yez.model.User;
import com.yez.service.UserService;
import com.yez.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/create-user")
    public ModelAndView showForm() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/validateUser")
    public ModelAndView checkValidation(@Validated @ModelAttribute("user") User user, BindingResult bindingResult) {
        new UserValidator().validate(user,bindingResult);

        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("index");
            return modelAndView;
        }
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("phoneNumber",user.getPhoneNumber());
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView showUserList(){
        List<User> users = userService.findAll();
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("users",users);
        return modelAndView;
    }

}
