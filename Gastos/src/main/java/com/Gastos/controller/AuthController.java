package com.Gastos.controller;

import com.Gastos.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/authenticate")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public String generateToken(@RequestParam String username) {
        // Generar un token usando el nombre de usuario
        return jwtUtil.generateToken(username);
    }
}
