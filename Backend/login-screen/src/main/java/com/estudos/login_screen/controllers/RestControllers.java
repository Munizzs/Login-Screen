package com.estudos.login_screen.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllers {

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping("/user")
    public String user(){
        return "user";
    }

    @GetMapping("/")
    public String nada(){
        return "Olá";
    }
}
