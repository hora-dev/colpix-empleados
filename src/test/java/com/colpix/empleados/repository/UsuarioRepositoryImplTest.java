package com.colpix.empleados.repository;


import com.colpix.empleados.application.service.UsuarioService;
import com.colpix.empleados.domain.Usuario;
import com.colpix.empleados.infraestructure.adapter.UsuarioCrudRepository;
import com.colpix.empleados.infraestructure.adapter.UsuarioRepositoryImpl;
import com.colpix.empleados.infraestructure.entity.UsuarioEntity;
import com.colpix.empleados.infraestructure.mapper.UsuarioMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioRepositoryImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioCrudRepository usuarioCrudRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioRepositoryImpl usuarioRepositoryImpl;

    @InjectMocks
    private UsuarioRepositoryImpl usuarioService;

    @Test
    void deberiaRetornarUsuarioCuandoExisteUsername() {

        String username = "admin";

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setUsername(username);

        Usuario usuario = new Usuario();
        usuario.setUsername(username);

        when(usuarioCrudRepository.findByUsername(username))
                .thenReturn(Optional.of(usuarioEntity));

        when(usuarioMapper.toUsuario(usuarioEntity))
                .thenReturn(usuario);

        Usuario resultado = usuarioRepositoryImpl.buscarPorUsername(username);

        assertNotNull(resultado);
        assertEquals(username, resultado.getUsername());

        verify(usuarioCrudRepository).findByUsername(username);
        verify(usuarioMapper).toUsuario(usuarioEntity);
    }

    @Test
    void deberiaRetornarNullCuandoNoExisteUsuario() {

        String username = "noexiste";

        when(usuarioCrudRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        Usuario resultado = usuarioRepositoryImpl.buscarPorUsername(username);

        assertNull(resultado);
        verify(usuarioCrudRepository).findByUsername(username);
        verify(usuarioMapper, never()).toUsuario(any());
    }

    @Test
    void deberiaCrearUsuario() {

        Usuario usuario = new Usuario();
        usuario.setUsername("admin");
        usuario.setPassword("1234");

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setUsername("admin");

        UsuarioEntity usuarioGuardado = new UsuarioEntity();
        usuarioGuardado.setUsername("admin");
        usuarioGuardado.setPassword("encrypted");

        Usuario usuarioFinal = new Usuario();
        usuarioFinal.setUsername("admin");

        when(usuarioCrudRepository.findByUsername("admin"))
                .thenReturn(Optional.empty());

        when(usuarioMapper.toUsuarioEntity(usuario))
                .thenReturn(usuarioEntity);

        when(passwordEncoder.encode("1234"))
                .thenReturn("encrypted");

        when(usuarioCrudRepository.save(usuarioEntity))
                .thenReturn(usuarioGuardado);

        when(usuarioMapper.toUsuario(usuarioGuardado))
                .thenReturn(usuarioFinal);

        Usuario resultado = usuarioRepositoryImpl.crearUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("admin", resultado.getUsername());

        verify(usuarioCrudRepository).findByUsername("admin");
        verify(passwordEncoder).encode("1234");
        verify(usuarioCrudRepository).save(usuarioEntity);
    }
}