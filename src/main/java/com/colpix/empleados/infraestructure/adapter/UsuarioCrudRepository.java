package com.colpix.empleados.infraestructure.adapter;

import com.colpix.empleados.infraestructure.entity.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioCrudRepository extends CrudRepository<UsuarioEntity, Integer> {
    Optional<UsuarioEntity> findByUsername(String username);
}
