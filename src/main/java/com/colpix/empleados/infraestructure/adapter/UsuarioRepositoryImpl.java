package com.colpix.empleados.infraestructure.adapter;

import com.colpix.empleados.application.repository.UsuarioRepository;
import com.colpix.empleados.domain.Usuario;
import com.colpix.empleados.infraestructure.entity.UsuarioEntity;
import com.colpix.empleados.infraestructure.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {
    private final UsuarioCrudRepository usuarioCrudRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Usuario crearUsuario(Usuario usuarios) {
        return null;
    }

    @Override
    public Usuario cambiarEstadoEnabled(Usuario usuario) {
        return null;
    }

    public Usuario buscarPorUsername(String username) {
        Optional<UsuarioEntity> usuarioEntity = usuarioCrudRepository.findByUsername(username);
        return usuarioEntity.map(usuarioMapper::toUsuario).orElse(null);
    }

}
