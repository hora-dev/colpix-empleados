package com.colpix.empleados.infraestructure.controller;

import com.colpix.empleados.infraestructure.configuration.JwtService;
import com.colpix.empleados.infraestructure.dto.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO request){

        // validar usuario (ejemplo simple)
        if(request.getUsername().equals("admin")
                && request.getPassword().equals("1234")) {

            return jwtService.generateToken(request.getUsername());
        }

        throw new RuntimeException("Credenciales inválidas");
    }
}