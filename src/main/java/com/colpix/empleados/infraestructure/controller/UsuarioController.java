package com.colpix.empleados.infraestructure.controller;

import com.colpix.empleados.application.service.UsuarioService;
import com.colpix.empleados.domain.Usuario;
import com.colpix.empleados.infraestructure.dto.UsuarioDTO;
import com.colpix.empleados.infraestructure.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping("/")
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.crearUsuario(usuarioMapper.toUsuarioFromDTO(usuarioDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toUsuarioDTO(usuario));
    }
}
