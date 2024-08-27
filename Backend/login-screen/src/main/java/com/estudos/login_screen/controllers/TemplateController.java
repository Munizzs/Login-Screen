package com.estudos.login_screen.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TemplateController {
    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("nome", username);
        return "home";
    }


}
