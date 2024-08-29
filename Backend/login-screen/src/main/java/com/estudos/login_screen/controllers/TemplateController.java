package com.estudos.login_screen.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TemplateController {
    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities  = authentication.getAuthorities();
        List<String> roles = authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(role -> role.replace("ROLE_",""))
                        .collect(Collectors.toList());
        model.addAttribute("nome", username);
        model.addAttribute("roles", roles);
        return "home";
    }
}
