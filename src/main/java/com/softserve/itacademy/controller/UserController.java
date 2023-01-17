package com.softserve.itacademy.controller;

import com.softserve.itacademy.exception.InvalidAccessException;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.security.PermissionValidator;
import com.softserve.itacademy.security.UserDetailsImpl;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    Logger logger = LoggerFactory.getLogger("UserController.class");

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        return "create-user";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "create-user";
        }
        user.setPassword(user.getPassword());
        user.setRole(roleService.readById(2));
        User newUser = userService.create(user);
        return "redirect:/todos/all/users/" + newUser.getId();
    }

    @GetMapping("/{id}/read")
    public String read(@PathVariable long id, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        User user = userService.readById(id);
        logger.info("userDetails name while reading user = "+userDetails.getUsername());
        PermissionValidator.validateOwnership(userDetails, user.getEmail());
        model.addAttribute("user", user);
        return "user-info";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable long id, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        User user = userService.readById(id);
        PermissionValidator.validateOwnership(userDetails, user.getEmail());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAll());
        return "update-user";
    }


    @PostMapping("/{id}/update")
    public String update(@PathVariable long id, Model model, @Validated @ModelAttribute("user") User user, @RequestParam("roleId") long roleId, BindingResult result) {
        User oldUser = userService.readById(id);
        if (result.hasErrors()) {
            user.setRole(oldUser.getRole());
            model.addAttribute("roles", roleService.getAll());
            return "update-user";
        }
        if (oldUser.getRole().getName().equals("USER")) {
            user.setRole(oldUser.getRole());
        } else {
            user.setRole(roleService.readById(roleId));
        }
        userService.update(user);
        return "redirect:/users/" + id + "/read";
    }


    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user  = userService.readById(id);
        PermissionValidator.validateOwnership(userDetails, user.getEmail());
        userService.delete(id);
        return "redirect:/users/all";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users-list";
    }
}
