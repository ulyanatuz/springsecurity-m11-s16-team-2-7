package com.softserve.itacademy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Qualifier("webSecurityConfig")
    @Autowired
    WebSecurityConfigurer configurer;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login-form")
    public String loginForm() {
        return "login-page";
    }

    @PostMapping("/login")
    public String login() {
         return "login-page";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

}
