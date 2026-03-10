package com.colpix.empleados.infraestructure.adapter;

import com.colpix.empleados.application.repository.UsuarioRepository;
import com.colpix.empleados.domain.Usuario;
import com.colpix.empleados.infraestructure.entity.UsuarioEntity;
import com.colpix.empleados.infraestructure.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {
    private final UsuarioCrudRepository usuarioCrudRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        Usuario usuarioFromDB = buscarPorUsername(!Objects.isNull(usuario) ? usuario.getUsername() : null);
        if(!Objects.isNull(usuarioFromDB)) {
            throw new IllegalArgumentException("El usuario con username " + usuarioFromDB.getUsername() + " ya existe");
        }

        UsuarioEntity usuarioEntity = usuarioMapper.toUsuarioEntity(usuario);
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuarioEntity.setPassword(passwordEncriptada);
        UsuarioEntity userSaved = usuarioCrudRepository.save(usuarioEntity);
        return usuarioMapper.toUsuario(userSaved);
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
