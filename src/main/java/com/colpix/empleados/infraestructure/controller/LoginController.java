package com.colpix.empleados.infraestructure.controller;

import com.colpix.empleados.application.service.UsuarioService;
import com.colpix.empleados.domain.Usuario;
import com.colpix.empleados.infraestructure.configuration.JwtService;
import com.colpix.empleados.infraestructure.dto.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request){

        Usuario usuario = usuarioService.buscarPorUsername(request.getUsername());
        if(Objects.isNull(usuario)){
           return ResponseEntity.notFound().build();
        }

        if(request.getUsername().equals(usuario.getUsername())
                && request.getPassword().equals(usuario.getPassword())) {

            String token = jwtService.generateToken(request.getUsername());
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
}