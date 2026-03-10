package com.colpix.empleados.application.repository;

import com.colpix.empleados.domain.Usuario;

public interface UsuarioRepository {
    Usuario crearUsuario(Usuario usuarios);
    Usuario cambiarEstadoEnabled(Usuario usuario);
    Usuario buscarPorUsername(String username);
}
