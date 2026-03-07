package com.colpix.empleados.application.repository;

import com.colpix.empleados.domain.Usuario;

public interface UsuarioRepository {
    Usuario crearUsuario(Usuario usuarios);
    Usuario cambiarContraseña(Usuario usuario, String contraseñaActual, String nuevaContraseña);
    Usuario cambiarEstadoEnabled(Usuario usuario);
}
