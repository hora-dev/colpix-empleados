package com.colpix.empleados.application.service;


import com.colpix.empleados.application.repository.UsuarioRepository;
import com.colpix.empleados.domain.Usuario;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario buscarPorUsername(String username) {
        return this.usuarioRepository.buscarPorUsername(username);
    }
}
