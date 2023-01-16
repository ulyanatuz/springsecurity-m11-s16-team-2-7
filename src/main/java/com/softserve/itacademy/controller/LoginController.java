package com.softserve.itacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/login-form")
    public String login(){
        return "login-page";
    }

    @PostMapping("/login-form")
    public String login(@RequestParam String username, @RequestParam String password){

    }
}
