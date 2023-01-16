package com.softserve.itacademy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login-form")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/login-form?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/perform-logout")
                .logoutSuccessUrl("/login-form")
                .deleteCookies("JSESSIONID");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws  Exception{
        auth.inMemoryAuthentication().withUser("Mike").password("mike").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("Nick").password("nick").roles("USER");
    }
}
